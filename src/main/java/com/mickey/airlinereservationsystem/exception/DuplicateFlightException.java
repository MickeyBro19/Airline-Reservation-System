package com.mickey.airlinereservationsystem.exception;

public class DuplicateFlightException extends RuntimeException{
    public DuplicateFlightException(String message)
    {
        super(message);
    }
}
