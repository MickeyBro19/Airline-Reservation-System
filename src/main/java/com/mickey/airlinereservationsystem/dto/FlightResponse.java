package com.mickey.airlinereservationsystem.dto;

import com.mickey.airlinereservationsystem.entity.Airport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(

        Long id,
        String flightNumber,
        String airline,
        Airport departureAirport,
        Airport arrivalAirport,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        BigDecimal ticketPrice,
        Integer availableSeats
) {
}
