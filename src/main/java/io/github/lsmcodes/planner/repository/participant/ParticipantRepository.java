package io.github.lsmcodes.planner.repository.participant;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.model.participant.Participant;
import io.github.lsmcodes.planner.model.trip.Trip;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

        List<Participant> findAllByTrip(Trip trip);
        boolean existsByEmail(String email);

}