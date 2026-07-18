package com.mickey.airlinereservationsystem.exception;

public class FlightAlreadyDepartedException extends RuntimeException {
    public FlightAlreadyDepartedException(String message) {
        super(message);
    }
}
