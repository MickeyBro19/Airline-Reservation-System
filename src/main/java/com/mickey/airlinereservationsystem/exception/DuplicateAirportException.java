package com.mickey.airlinereservationsystem.exception;

public class DuplicateAirportException extends RuntimeException{
    public DuplicateAirportException(String message){
        super(message);
    }
}
