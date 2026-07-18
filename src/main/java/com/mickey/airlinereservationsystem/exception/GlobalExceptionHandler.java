package com.mickey.airlinereservationsystem.exception;

import com.mickey.airlinereservationsystem.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFound(
            FlightNotFoundException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Flight Not Found!",
                ex.getMessage(),
                request
        );
    }
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAirportNotFound(
            AirportNotFoundException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Airport Not Found!",
                ex.getMessage(),
                request
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(
          UserNotFoundException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "User Not Found!",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(DuplicateAirportException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAirport(
            DuplicateAirportException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Airport Already Exists!",
                ex.getMessage(),
                request
        );
    }
    @ExceptionHandler(DuplicateFlightException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateFlight(
            DuplicateFlightException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Flight Already Exists!",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(NoSeatsAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNoSeatsAvailable(
            NoSeatsAvailableException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "No Seats Available!",
                ex.getMessage(),
                request
        );
    }
    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadyBooked(
            SeatAlreadyBookedException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Another passenger booked this seat. Please try another seat.",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(FlightAlreadyDepartedException.class)
    public ResponseEntity<ErrorResponse> handleFlightAlreadyDeparted(
            FlightAlreadyDepartedException ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Flight Departed. Can't book seats!",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something Went Wrong!",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                message,
                request
        );
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleConcurrency(
            ObjectOptimisticLockingFailureException ex,
            HttpServletRequest request) {


        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Seat Already Booked",
                "Another passenger booked this seat. Please choose another seat.",
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }
}
