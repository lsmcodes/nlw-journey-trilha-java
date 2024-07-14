package io.github.lsmcodes.planner.dto.model.participant;

import jakarta.validation.constraints.NotNull;

public record ParticipantRequestPayload(
        @NotNull
        String name,
        
        @NotNull
        String email) {

}