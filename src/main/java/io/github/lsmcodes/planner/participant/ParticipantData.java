package io.github.lsmcodes.planner.participant;

import java.util.UUID;

public record ParticipantData(UUID id, String name, String email, boolean isConfirmed) {

        public ParticipantData(Participant participant) {
                this(participant.getId(), participant.getName(), participant.getEmail(), participant.isConfirmed());
        }

}