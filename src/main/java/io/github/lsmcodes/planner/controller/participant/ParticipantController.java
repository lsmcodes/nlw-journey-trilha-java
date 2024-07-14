package io.github.lsmcodes.planner.controller.participant;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.planner.dto.model.participant.ParticipantData;
import io.github.lsmcodes.planner.dto.model.participant.ParticipantRequestPayload;
import io.github.lsmcodes.planner.dto.response.Response;
import io.github.lsmcodes.planner.exception.ParticipantNotFoundException;
import io.github.lsmcodes.planner.model.participant.Participant;
import io.github.lsmcodes.planner.service.participant.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

        @Autowired
        private ParticipantService service;

        @PostMapping("/{id}/confirm")
        public ResponseEntity<Response<ParticipantData>> confirmParticipant(@PathVariable UUID id,
                        @RequestBody ParticipantRequestPayload payload) throws ParticipantNotFoundException {
                Participant participant = service.getParticipantById(id);

                participant.setConfirmed(true);
                participant.setName(payload.name());

                this.service.saveParticipant(participant);

                Response<ParticipantData> response = new Response<ParticipantData>(LocalDateTime.now(), HttpStatus.OK,
                                new ParticipantData(participant), null);

                return ResponseEntity.ok(response);
        }

}