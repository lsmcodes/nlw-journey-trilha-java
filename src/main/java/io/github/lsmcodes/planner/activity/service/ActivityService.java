package io.github.lsmcodes.planner.activity.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.activity.dto.ActivityRequestPayload;
import io.github.lsmcodes.planner.activity.model.Activity;
import io.github.lsmcodes.planner.activity.repository.ActivityRepository;
import io.github.lsmcodes.planner.trip.model.Trip;

@Service
public class ActivityService {

        @Autowired
        private ActivityRepository repository;

        public UUID registerActivityToTrip(ActivityRequestPayload payload, Trip trip) {
                Activity activity = new Activity(payload.title(), payload.occursAt(), trip);

                this.repository.save(activity);
                return activity.getId();
        }

        public List<Activity> getActivitiesByTrip(Trip trip) {
                List<Activity> activities = this.repository.findAllByTrip(trip);
                return activities;
        }

}