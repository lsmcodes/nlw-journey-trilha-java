package io.github.lsmcodes.planner.trip.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.trip.model.Trip;

public interface TripRepository extends JpaRepository<Trip, UUID> {

}