package io.github.lsmcodes.planner.activity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.activity.model.Activity;
import io.github.lsmcodes.planner.trip.model.Trip;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

        List<Activity> findAllByTrip(Trip trip);

}