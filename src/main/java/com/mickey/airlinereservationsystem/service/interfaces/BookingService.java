package com.mickey.airlinereservationsystem.service.interfaces;

import com.mickey.airlinereservationsystem.dto.BookingRequest;
import com.mickey.airlinereservationsystem.dto.BookingResponse;

public interface BookingService {
    BookingResponse bookFlight(BookingRequest request);
}
