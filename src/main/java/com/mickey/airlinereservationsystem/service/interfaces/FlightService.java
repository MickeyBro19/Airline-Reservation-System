package com.mickey.airlinereservationsystem.service.interfaces;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import com.mickey.airlinereservationsystem.entity.Flight;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);

    FlightResponse getFlight(String flightNumber);

    Page<FlightResponse> getAllFlights(
            int page,
            int size,
            String sortBy);

    void deleteFlight(String flightNumber);

    public Page<FlightResponse> searchFlights(
            String from,
            String to,
            LocalDate date,
            int page,
            int size,
            String sortBy);
}
