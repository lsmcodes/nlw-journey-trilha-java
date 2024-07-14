package io.github.lsmcodes.planner.service.trip;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.dto.model.trip.TripRequestPayload;
import io.github.lsmcodes.planner.exception.TripNotFoundException;
import io.github.lsmcodes.planner.model.trip.Trip;
import io.github.lsmcodes.planner.repository.trip.TripRepository;

@Service
public class TripService {

        @Autowired
        private TripRepository repository;

        public void saveTrip(Trip trip) {
                this.repository.save(trip);
        }

        public void updateTrip(TripRequestPayload payload, Trip trip) {
                trip.setDestination(payload.destination());
                trip.setStartsAt(payload.startsAt());
                trip.setEndsAt(payload.endsAt());

                this.repository.save(trip);
        }

        public Trip getTripById(UUID id) throws TripNotFoundException {
                return this.repository.findById(id).orElseThrow(() -> new TripNotFoundException("No trip found with the id " + id));
        }

}