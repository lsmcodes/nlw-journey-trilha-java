package io.github.lsmcodes.planner.trip.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.planner.activity.dto.ActivityCreatedResponse;
import io.github.lsmcodes.planner.activity.dto.ActivityData;
import io.github.lsmcodes.planner.activity.dto.ActivityRequestPayload;
import io.github.lsmcodes.planner.activity.model.Activity;
import io.github.lsmcodes.planner.activity.service.ActivityService;
import io.github.lsmcodes.planner.link.dto.LinkCreatedResponse;
import io.github.lsmcodes.planner.link.dto.LinkData;
import io.github.lsmcodes.planner.link.dto.LinkRequestPayload;
import io.github.lsmcodes.planner.link.model.Link;
import io.github.lsmcodes.planner.link.service.LinkService;
import io.github.lsmcodes.planner.participant.dto.ParticipantCreateResponse;
import io.github.lsmcodes.planner.participant.dto.ParticipantData;
import io.github.lsmcodes.planner.participant.dto.ParticipantRequestPayload;
import io.github.lsmcodes.planner.participant.model.Participant;
import io.github.lsmcodes.planner.participant.service.ParticipantService;
import io.github.lsmcodes.planner.trip.dto.TripCreatedResponse;
import io.github.lsmcodes.planner.trip.dto.TripData;
import io.github.lsmcodes.planner.trip.dto.TripRequestPayload;
import io.github.lsmcodes.planner.trip.model.Trip;
import io.github.lsmcodes.planner.trip.service.TripService;

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
        public ResponseEntity<TripCreatedResponse> createTrip(@RequestBody TripRequestPayload payload) {
                Trip newTrip = new Trip(payload);

                this.tripService.saveTrip(newTrip);
                this.participantService.registerParticipantsToTrip(payload.emailsToInvite(), newTrip);

                return ResponseEntity.ok(new TripCreatedResponse(newTrip.getId()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<TripData> getTripDetails(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                return optionalTrip.map(trip -> ResponseEntity.ok(new TripData(trip)))
                                .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<TripData> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();

                        this.tripService.updateTrip(payload, trip);

                        return ResponseEntity.ok(new TripData(trip));
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/confirm")
        public ResponseEntity<TripData> confirmTrip(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();

                        trip.setIsConfirmed(true);

                        this.tripService.saveTrip(trip);
                        this.participantService.triggerConfirmationEmailToParticipants(id);

                        return ResponseEntity.ok(new TripData(trip));
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/invite")
        public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id,
                        @RequestBody ParticipantRequestPayload payload) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();

                        UUID invitedParticipantId = this.participantService.registerParticipantToTrip(payload.email(),
                                        trip);

                        if (trip.getIsConfirmed()) {
                                this.participantService.triggerConfirmationEmailToParticipant(payload.email());
                        }

                        return ResponseEntity.ok(new ParticipantCreateResponse(invitedParticipantId));
                }

                return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/participants")
        public ResponseEntity<List<ParticipantData>> getTripParticipants(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        List<Participant> tripParticipants = this.participantService.getParticipantsByTrip(trip);
                        List<ParticipantData> tripParticipantsResponse = tripParticipants.stream()
                                        .map(ParticipantData::new).toList();

                        return ResponseEntity.ok(tripParticipantsResponse);
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/activities")
        public ResponseEntity<ActivityCreatedResponse> registerActivity(@PathVariable UUID id,
                        @RequestBody ActivityRequestPayload payload) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        UUID activityId = this.activityService.registerActivityToTrip(payload, trip);

                        return ResponseEntity.ok(new ActivityCreatedResponse(activityId));
                }

                return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/activities")
        public ResponseEntity<List<ActivityData>> getTripActivities(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        List<Activity> tripActivities = this.activityService.getActivitiesByTrip(trip);
                        List<ActivityData> tripActivitiesResponse = tripActivities.stream().map(ActivityData::new)
                                        .toList();

                        return ResponseEntity.ok(tripActivitiesResponse);
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/links")
        public ResponseEntity<LinkCreatedResponse> registerLink(@PathVariable UUID id,
                        @RequestBody LinkRequestPayload payload) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        UUID linkId = this.linkService.registerLinkToTrip(payload, trip);

                        return ResponseEntity.ok(new LinkCreatedResponse(linkId));
                }

                return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/links")
        public ResponseEntity<List<LinkData>> getTripLinks(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.tripService.getTripById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        List<Link> tripLinks = this.linkService.getLinksByTrip(trip);
                        List<LinkData> tripLinksResponse = tripLinks.stream().map(LinkData::new).toList();

                        return ResponseEntity.ok(tripLinksResponse);
                }

                return ResponseEntity.notFound().build();
        }

}