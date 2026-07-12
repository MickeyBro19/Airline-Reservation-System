package com.mickey.airlinereservationsystem.dto;

public record BookingRequest(
        Long userId,
        String flightNumber,
        String seatNumber
) {
}
