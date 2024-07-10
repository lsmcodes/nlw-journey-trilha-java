package io.github.lsmcodes.planner.participant;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.trip.Trip;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

        List<Participant> findAllByTrip(Trip trip);

}