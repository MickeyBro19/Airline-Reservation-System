package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightRequest(
        @NotBlank
        String flightNumber,

        @NotBlank
        String airline,

        @NotBlank
        String departureAirportCode,

        @NotBlank
        String arrivalAirportCode,

        @Future
        LocalDateTime departureTime,

        @Future
        LocalDateTime arrivalTime,

        @Positive
        BigDecimal ticketPrice,

        @Positive
        Integer totalSeats
) {
}
