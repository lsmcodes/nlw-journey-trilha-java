package io.github.lsmcodes.planner.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(LocalDateTime timestamp, HttpStatus status, T data, Object errors) {

}