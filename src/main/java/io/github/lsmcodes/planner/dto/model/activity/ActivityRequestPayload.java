package io.github.lsmcodes.planner.dto.model.activity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import jakarta.validation.constraints.NotNull;

public record ActivityRequestPayload(
                @NotNull(message = "Title cannot be null") String title,

                @NotNull(message = "Ends at cannot be null") @JsonSerialize(using = ToStringSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East") LocalDateTime occursAt) {

}