package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotNull
        Long userId,
        @NotNull
        String flightNumber,
        @NotNull
        String seatNumber
) {
}
