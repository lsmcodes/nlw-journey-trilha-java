package io.github.lsmcodes.planner.trip.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.trip.dto.TripRequestPayload;
import io.github.lsmcodes.planner.trip.model.Trip;
import io.github.lsmcodes.planner.trip.repository.TripRepository;

@Service
public class TripService {

        @Autowired
        private TripRepository repository;

        public void saveTrip(Trip trip) {
                this.repository.save(trip);
        }

        public void updateTrip(TripRequestPayload payload, Trip trip) {
                trip.setDestination(payload.destination());
                trip.setStartsAt(LocalDateTime.parse(payload.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
                trip.setEndsAt(LocalDateTime.parse(payload.endsAt(), DateTimeFormatter.ISO_DATE_TIME));

                this.repository.save(trip);
        }

        public Optional<Trip> getTripById(UUID id) {
                Optional<Trip> optionalTrip = this.repository.findById(id);
                return optionalTrip;
        }

}