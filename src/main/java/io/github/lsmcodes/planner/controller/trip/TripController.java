package io.github.lsmcodes.planner.controller.trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.planner.dto.model.activity.ActivityData;
import io.github.lsmcodes.planner.dto.model.activity.ActivityRequestPayload;
import io.github.lsmcodes.planner.dto.model.link.LinkData;
import io.github.lsmcodes.planner.dto.model.link.LinkRequestPayload;
import io.github.lsmcodes.planner.dto.model.participant.ParticipantData;
import io.github.lsmcodes.planner.dto.model.participant.ParticipantRequestPayload;
import io.github.lsmcodes.planner.dto.model.trip.TripData;
import io.github.lsmcodes.planner.dto.model.trip.TripRequestPayload;
import io.github.lsmcodes.planner.dto.response.Response;
import io.github.lsmcodes.planner.exception.ActivityDataOutOfTripPeriodException;
import io.github.lsmcodes.planner.exception.EmailAlreadyRegisteredException;
import io.github.lsmcodes.planner.exception.InvalidDateRangeException;
import io.github.lsmcodes.planner.exception.TripNotFoundException;
import io.github.lsmcodes.planner.model.activity.Activity;
import io.github.lsmcodes.planner.model.link.Link;
import io.github.lsmcodes.planner.model.participant.Participant;
import io.github.lsmcodes.planner.model.trip.Trip;
import io.github.lsmcodes.planner.service.activity.ActivityService;
import io.github.lsmcodes.planner.service.link.LinkService;
import io.github.lsmcodes.planner.service.participant.ParticipantService;
import io.github.lsmcodes.planner.service.trip.TripService;
import io.github.lsmcodes.planner.util.PlannerUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/trips")
public class TripController {

        @Autowired
        private TripService tripService;

        @Autowired
        private ParticipantService participantService;

        @Autowired
        private ActivityService activityService;

        @Autowired
        private LinkService linkService;

        @PostMapping
        public ResponseEntity<Response<UUID>> createTrip(@RequestBody @Valid TripRequestPayload payload,
                        BindingResult result)
                        throws InvalidDateRangeException {
                Response<UUID> response = null;

                if (result.hasErrors()) {
                        response = new Response<UUID>(LocalDateTime.now(), HttpStatus.BAD_REQUEST, null,
                                        PlannerUtil.getErrorMessagesFromBindingResult(result));

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                if (PlannerUtil.isEndDateBeforeStartDate(payload)) {
                        throw new InvalidDateRangeException("The end date cannot be before the start date");
                }

                Trip newTrip = new Trip(payload);
                this.tripService.saveTrip(newTrip);
                this.participantService.registerParticipantsToTrip(payload.emailsToInvite(), newTrip);

                response = new Response<UUID>(LocalDateTime.now(), HttpStatus.OK, newTrip.getId(), null);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Response<TripData>> getTripDetails(@PathVariable UUID id) throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                Response<TripData> response = new Response<TripData>(LocalDateTime.now(), HttpStatus.OK,
                                new TripData(trip), null);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Response<TripData>> updateTrip(@PathVariable UUID id,
                        @RequestBody TripRequestPayload payload)
                        throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                this.tripService.updateTrip(payload, trip);
                Response<TripData> response = new Response<TripData>(LocalDateTime.now(), HttpStatus.OK,
                                new TripData(trip), null);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @PostMapping("/{id}/confirm")
        public ResponseEntity<Response<TripData>> confirmTrip(@PathVariable UUID id) throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                trip.setIsConfirmed(true);
                this.tripService.saveTrip(trip);
                this.participantService.triggerConfirmationEmailToParticipants(id);

                Response<TripData> response = new Response<TripData>(LocalDateTime.now(), HttpStatus.OK,
                                new TripData(trip), null);

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{id}/invite")
        public ResponseEntity<Response<UUID>> inviteParticipant(@PathVariable UUID id,
                        @RequestBody ParticipantRequestPayload payload)
                        throws TripNotFoundException, EmailAlreadyRegisteredException {
                Trip trip = this.tripService.getTripById(id);
                List<Participant> participants = participantService.getParticipantsByTrip(trip);

                if (PlannerUtil.isTheEmailAlreadyRegistered(participants, payload)) {
                        throw new EmailAlreadyRegisteredException("The provided email is already registered");
                }

                UUID invitedParticipantId = this.participantService.registerParticipantToTrip(payload.email(), trip);

                if (trip.getIsConfirmed()) {
                        this.participantService.triggerConfirmationEmailToParticipant(payload.email());
                }

                Response<UUID> response = new Response<UUID>(LocalDateTime.now(), HttpStatus.OK, invitedParticipantId,
                                null);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @GetMapping("/{id}/participants")
        public ResponseEntity<Response<List<ParticipantData>>> getTripParticipants(@PathVariable UUID id)
                        throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                List<Participant> tripParticipants = this.participantService.getParticipantsByTrip(trip);
                List<ParticipantData> tripParticipantsResponse = tripParticipants.stream()
                                .map(ParticipantData::new).toList();

                Response<List<ParticipantData>> response = new Response<List<ParticipantData>>(LocalDateTime.now(),
                                HttpStatus.OK, tripParticipantsResponse, null);

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{id}/activities")
        public ResponseEntity<Response<UUID>> registerActivity(@PathVariable UUID id,
                        @RequestBody ActivityRequestPayload payload)
                        throws TripNotFoundException, ActivityDataOutOfTripPeriodException {
                Trip trip = this.tripService.getTripById(id);

                if (!PlannerUtil.isActivityDataWithinTripPeriod(payload, trip)) {
                        throw new ActivityDataOutOfTripPeriodException(
                                        "The activity date cannot be out of the trip period");
                }

                UUID activityId = this.activityService.registerActivityToTrip(payload, trip);

                Response<UUID> response = new Response<UUID>(LocalDateTime.now(), HttpStatus.OK, activityId, null);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}/activities")
        public ResponseEntity<Response<List<ActivityData>>> getTripActivities(@PathVariable UUID id)
                        throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                List<Activity> tripActivities = this.activityService.getActivitiesByTrip(trip);
                List<ActivityData> tripActivitiesResponse = tripActivities.stream().map(ActivityData::new)
                                .toList();

                Response<List<ActivityData>> response = new Response<List<ActivityData>>(LocalDateTime.now(),
                                HttpStatus.OK, tripActivitiesResponse, null);

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{id}/links")
        public ResponseEntity<Response<UUID>> registerLink(@PathVariable UUID id,
                        @RequestBody LinkRequestPayload payload) throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                UUID linkId = this.linkService.registerLinkToTrip(payload, trip);

                Response<UUID> response = new Response<UUID>(LocalDateTime.now(), HttpStatus.OK, linkId, null);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @GetMapping("/{id}/links")
        public ResponseEntity<Response<List<LinkData>>> getTripLinks(@PathVariable UUID id)
                        throws TripNotFoundException {
                Trip trip = this.tripService.getTripById(id);

                List<Link> tripLinks = this.linkService.getLinksByTrip(trip);
                List<LinkData> tripLinksResponse = tripLinks.stream().map(LinkData::new).toList();

                Response<List<LinkData>> response = new Response<List<LinkData>>(LocalDateTime.now(), HttpStatus.OK,
                                tripLinksResponse, null);

                return ResponseEntity.ok(response);
        }

}