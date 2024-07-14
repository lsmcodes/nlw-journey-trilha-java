package io.github.lsmcodes.planner.repository.activity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.model.activity.Activity;
import io.github.lsmcodes.planner.model.trip.Trip;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

        List<Activity> findAllByTrip(Trip trip);

}