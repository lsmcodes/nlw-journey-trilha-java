package io.github.lsmcodes.planner.dto.model.trip;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import jakarta.validation.constraints.NotNull;

public record TripRequestPayload(
                @NotNull(message = "Destination cannot be null") String destination,

                @NotNull(message = "Starts at cannot be null") @JsonSerialize(using = ToStringSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East") LocalDateTime startsAt,

                @NotNull(message = "Ends at cannot be null") @JsonSerialize(using = ToStringSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East") LocalDateTime endsAt,

                @NotNull(message = "Emails to invite cannot be null") Set<String> emailsToInvite,

                @NotNull(message = "Owner name cannot be null") String ownerName,

                @NotNull(message = "Owner email cannot be null") String ownerEmail) {

}