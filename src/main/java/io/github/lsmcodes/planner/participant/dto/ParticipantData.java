package io.github.lsmcodes.planner.participant.dto;

import java.util.UUID;

import io.github.lsmcodes.planner.participant.model.Participant;

public record ParticipantData(UUID id, String name, String email, boolean isConfirmed) {

        public ParticipantData(Participant participant) {
                this(participant.getId(), participant.getName(), participant.getEmail(), participant.isConfirmed());
        }

}