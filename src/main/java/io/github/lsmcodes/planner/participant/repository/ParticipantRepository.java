package io.github.lsmcodes.planner.participant.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.participant.model.Participant;
import io.github.lsmcodes.planner.trip.model.Trip;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

        List<Participant> findAllByTrip(Trip trip);

}