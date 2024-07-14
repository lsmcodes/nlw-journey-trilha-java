package io.github.lsmcodes.planner.dto.model.activity;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.lsmcodes.planner.model.activity.Activity;

public record ActivityData(UUID id, String title, LocalDateTime occursAt) {

        public ActivityData(Activity activity) {
                this(activity.getId(), activity.getTitle(), activity.getOccursAt());
        }

}