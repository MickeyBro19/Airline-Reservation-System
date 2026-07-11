package com.mickey.airlinereservationsystem.service.interfaces;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import org.springframework.data.domain.Page;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);

    FlightResponse getFlight(String flightNumber);

    Page<FlightResponse> getAllFlights(
            int page,
            int size,
            String sortBy);

    void deleteFlight(String flightNumber);
}
