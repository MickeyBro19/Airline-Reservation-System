package com.mickey.airlinereservationsystem.controller;

import com.mickey.airlinereservationsystem.dto.BookingRequest;
import com.mickey.airlinereservationsystem.dto.BookingResponse;
import com.mickey.airlinereservationsystem.service.interfaces.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse bookFlight(
            @Valid @RequestBody BookingRequest request) {

        return bookingService.bookFlight(request);
    }
}