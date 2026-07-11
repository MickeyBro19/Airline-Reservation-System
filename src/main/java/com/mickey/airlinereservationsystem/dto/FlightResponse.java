package com.mickey.airlinereservationsystem.dto;

import com.mickey.airlinereservationsystem.entity.Airport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(

        String flightNumber,
        String airline,
        String departureAirport,
        String arrivalAirport,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        BigDecimal ticketPrice,
        Integer availableSeats
) {
}
