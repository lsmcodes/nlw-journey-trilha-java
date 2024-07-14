package io.github.lsmcodes.planner.util;

import java.util.List;

import org.springframework.validation.BindingResult;

import io.github.lsmcodes.planner.dto.model.activity.ActivityRequestPayload;
import io.github.lsmcodes.planner.dto.model.participant.ParticipantRequestPayload;
import io.github.lsmcodes.planner.dto.model.trip.TripRequestPayload;
import io.github.lsmcodes.planner.model.participant.Participant;
import io.github.lsmcodes.planner.model.trip.Trip;

public class PlannerUtil {

        public static boolean isEndDateBeforeStartDate(TripRequestPayload payload) {
                return payload.endsAt().isBefore(payload.startsAt());
        }

        public static boolean isActivityDataWithinTripPeriod(ActivityRequestPayload payload, Trip trip) {
                return (payload.occursAt().isAfter(trip.getStartsAt()) && payload.occursAt().isBefore(trip.getEndsAt()))
                                || payload.occursAt().isEqual(trip.getStartsAt())
                                || payload.occursAt().isEqual(trip.getEndsAt());
        }

        public static boolean isTheEmailAlreadyRegistered(List<Participant> participants, ParticipantRequestPayload payload) {
                return participants.stream().anyMatch(participant -> participant.getEmail().equals(payload.email()));
        }

        public static List<String> getErrorMessagesFromBindingResult(BindingResult result) {
                return result.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
        }

}