package com.mickey.airlinereservationsystem.dto;

import com.mickey.airlinereservationsystem.enums.TicketStatus;

public record BookingResponse(
        String bookingReference,
        String passenger,
        String flightNumber,
        String seatNumber,
        TicketStatus status
) {
}
