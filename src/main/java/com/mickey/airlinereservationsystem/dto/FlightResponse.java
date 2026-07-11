package com.mickey.airlinereservationsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(

        Long id,
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
