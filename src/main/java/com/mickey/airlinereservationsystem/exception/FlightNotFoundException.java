package com.mickey.airlinereservationsystem.exception;

public class FlightNotFoundException  extends RuntimeException{
    public FlightNotFoundException(String message)
    {
        super(message);
    }
}
