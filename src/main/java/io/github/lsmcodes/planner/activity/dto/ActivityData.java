package io.github.lsmcodes.planner.activity.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.lsmcodes.planner.activity.model.Activity;

public record ActivityData(UUID id, String title, LocalDateTime occursAt) {

        public ActivityData(Activity activity) {
                this(activity.getId(), activity.getTitle(), activity.getOccursAt());
        }

}