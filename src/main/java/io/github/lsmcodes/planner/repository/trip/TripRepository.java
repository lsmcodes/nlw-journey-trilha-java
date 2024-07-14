package io.github.lsmcodes.planner.repository.trip;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.model.trip.Trip;

public interface TripRepository extends JpaRepository<Trip, UUID> {

}