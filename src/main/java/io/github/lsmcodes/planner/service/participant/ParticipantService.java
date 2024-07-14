package io.github.lsmcodes.planner.service.participant;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.exception.ParticipantNotFoundException;
import io.github.lsmcodes.planner.model.participant.Participant;
import io.github.lsmcodes.planner.model.trip.Trip;
import io.github.lsmcodes.planner.repository.participant.ParticipantRepository;

@Service
public class ParticipantService {

        @Autowired
        private ParticipantRepository repository;

        public void saveParticipant(Participant participant) {
                this.repository.save(participant);
        }

        public void registerParticipantsToTrip(Set<String> participantsToInvite, Trip trip) {
                List<Participant> participants = participantsToInvite.stream()
                                .map(email -> new Participant(email, trip)).toList();

                repository.saveAll(participants);
        }

        public UUID registerParticipantToTrip(String participantToInvite, Trip trip) {
                Participant newParticipant = new Participant(participantToInvite, trip);
                
                repository.save(newParticipant);

                return newParticipant.getId();
        }

        public Participant getParticipantById(UUID id) throws ParticipantNotFoundException {
                return this.repository.findById(id).orElseThrow(() -> new ParticipantNotFoundException("No participant found with the id " + id));
        }

        public List<Participant> getParticipantsByTrip(Trip trip) {
                return this.repository.findAllByTrip(trip);
        }

        public void triggerConfirmationEmailToParticipants(UUID tripId) {

        }

        public void triggerConfirmationEmailToParticipant(String email) {

        }

}