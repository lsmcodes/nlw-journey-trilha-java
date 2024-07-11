package io.github.lsmcodes.planner.participant.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.planner.participant.dto.ParticipantData;
import io.github.lsmcodes.planner.participant.dto.ParticipantRequestPayload;
import io.github.lsmcodes.planner.participant.model.Participant;
import io.github.lsmcodes.planner.participant.repository.ParticipantRepository;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

        @Autowired
        private ParticipantRepository repository;

        @PostMapping("/{id}/confirm")
        public ResponseEntity<ParticipantData> confirmParticipant(@PathVariable UUID id,
                        @RequestBody ParticipantRequestPayload payload) {
                Optional<Participant> optionalParticipant = this.repository.findById(id);

                if (optionalParticipant.isPresent()) {
                        Participant participant = optionalParticipant.get();

                        participant.setConfirmed(true);
                        participant.setName(payload.name());

                        this.repository.save(participant);

                        return ResponseEntity.ok(new ParticipantData(participant));
                }

                return ResponseEntity.notFound().build();
        }

}