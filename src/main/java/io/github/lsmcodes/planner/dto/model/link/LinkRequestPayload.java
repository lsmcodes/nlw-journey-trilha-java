package io.github.lsmcodes.planner.dto.model.link;

import jakarta.validation.constraints.NotNull;

public record LinkRequestPayload(
                @NotNull String title,

                @NotNull String url) {

}