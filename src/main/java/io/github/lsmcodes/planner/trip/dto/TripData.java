package io.github.lsmcodes.planner.trip.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.lsmcodes.planner.trip.model.Trip;

public record TripData(UUID id, String destination, LocalDateTime startsAt, LocalDateTime endsAt, boolean isConfirmed,
                String ownerName, String ownerEmail) {

        public TripData(Trip trip) {
                this(trip.getId(), trip.getDestination(), trip.getStartsAt(), trip.getEndsAt(), trip.getIsConfirmed(),
                                trip.getOwnerName(), trip.getOwnerEmail());
        }

}