package io.github.lsmcodes.planner.service.activity;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.dto.model.activity.ActivityRequestPayload;
import io.github.lsmcodes.planner.model.activity.Activity;
import io.github.lsmcodes.planner.model.trip.Trip;
import io.github.lsmcodes.planner.repository.activity.ActivityRepository;

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
                return this.repository.findAllByTrip(trip);
        }

}