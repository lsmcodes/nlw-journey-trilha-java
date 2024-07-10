package io.github.lsmcodes.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import io.github.lsmcodes.planner.participant.Participant;
import io.github.lsmcodes.planner.participant.ParticipantCreateResponse;
import io.github.lsmcodes.planner.participant.ParticipantData;
import io.github.lsmcodes.planner.participant.ParticipantRequestPayload;
import io.github.lsmcodes.planner.participant.ParticipantService;

@RestController
@RequestMapping("/trips")
public class TripController {

        @Autowired
        private ParticipantService participantService;

        @Autowired
        private TripRepository repository;

        @PostMapping
        public ResponseEntity<TripCreatedResponse> createTrip(@RequestBody TripRequestPayload payload) {
                Trip newTrip = new Trip(payload);

                this.repository.save(newTrip);
                this.participantService.registerParticipantsToTrip(payload.emailsToInvite(), newTrip);

                return ResponseEntity.ok(new TripCreatedResponse(newTrip.getId()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<TripData> getTripDetails(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.repository.findById(id);

                return optionalTrip.map(trip -> ResponseEntity.ok(new TripData(trip)))
                                .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<TripData> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
                Optional<Trip> optionalTrip = this.repository.findById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();

                        trip.setDestination(payload.destination());
                        trip.setStartsAt(LocalDateTime.parse(payload.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
                        trip.setEndsAt(LocalDateTime.parse(payload.endsAt(), DateTimeFormatter.ISO_DATE_TIME));

                        this.repository.save(trip);

                        return ResponseEntity.ok(new TripData(trip));
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/confirm")
        public ResponseEntity<TripData> confirmTrip(@PathVariable UUID id) {
                Optional<Trip> optionalTrip = this.repository.findById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();

                        trip.setIsConfirmed(true);

                        this.repository.save(trip);
                        this.participantService.triggerConfirmationEmailToParticipants(id);

                        return ResponseEntity.ok(new TripData(trip));
                }

                return ResponseEntity.notFound().build();
        }

        @PostMapping("/{id}/invite")
        public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id,
                        @RequestBody ParticipantRequestPayload payload) {
                Optional<Trip> optionalTrip = this.repository.findById(id);

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
                Optional<Trip> optionalTrip = this.repository.findById(id);

                if (optionalTrip.isPresent()) {
                        Trip trip = optionalTrip.get();
                        List<Participant> tripParticipants = this.participantService.getParticipantsByTrip(trip);
                        List<ParticipantData> tripParticipantsResponse = tripParticipants.stream()
                                        .map(ParticipantData::new).toList();

                        return ResponseEntity.ok(tripParticipantsResponse);
                }

                return ResponseEntity.notFound().build();
        }

}