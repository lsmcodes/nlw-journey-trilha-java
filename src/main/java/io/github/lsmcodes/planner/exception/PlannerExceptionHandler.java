package io.github.lsmcodes.planner.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

import io.github.lsmcodes.planner.dto.response.Response;

@ControllerAdvice
public class PlannerExceptionHandler<T> {

        @ExceptionHandler(value = { ActivityDataOutOfTripPeriodException.class })
        protected ResponseEntity<Response<T>> handleActivityDataOutOfTripPeriodException(
                        ActivityDataOutOfTripPeriodException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(value = { EmailAlreadyRegisteredException.class })
        protected ResponseEntity<Response<T>> handleEmailAlreadyRegisteredException(
                        EmailAlreadyRegisteredException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.CONFLICT, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler(value = { ParticipantNotFoundException.class })
        protected ResponseEntity<Response<T>> handleParticipantNotFoundException(
                        ParticipantNotFoundException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.NOT_FOUND, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(value = { InvalidDateRangeException.class })
        protected ResponseEntity<Response<T>> handleInvalidDateRangeException(
                        InvalidDateRangeException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(value = { TripNotFoundException.class })
        protected ResponseEntity<Response<T>> handleTripNotFoundException(
                        TripNotFoundException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.NOT_FOUND, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(value = { ServerErrorException.class })
        protected ResponseEntity<Response<T>> handleServerError(ServerErrorException exception) {
                Response<T> response = new Response<>(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, null,
                                exception.getLocalizedMessage());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

}