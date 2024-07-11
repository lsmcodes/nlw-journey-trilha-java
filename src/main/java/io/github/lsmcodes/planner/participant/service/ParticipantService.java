package io.github.lsmcodes.planner.participant.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.participant.model.Participant;
import io.github.lsmcodes.planner.participant.repository.ParticipantRepository;
import io.github.lsmcodes.planner.trip.model.Trip;

@Service
public class ParticipantService {

        @Autowired
        private ParticipantRepository repository;

        public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
                List<Participant> participants = participantsToInvite.stream()
                                .map(email -> new Participant(email, trip)).toList();

                repository.saveAll(participants);
        }

        public UUID registerParticipantToTrip(String participantToInvite, Trip trip) {
                Participant newParticipant = new Participant(participantToInvite, trip);

                repository.save(newParticipant);
                return newParticipant.getId();
        }

        public void triggerConfirmationEmailToParticipants(UUID tripId) {

        }

        public void triggerConfirmationEmailToParticipant(String email) {

        }

        public List<Participant> getParticipantsByTrip(Trip trip) {
                return this.repository.findAllByTrip(trip);
        }

}