package com.mickey.airlinereservationsystem.controller;
import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import com.mickey.airlinereservationsystem.service.interfaces.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;


    @GetMapping
    public Page<FlightResponse> getAllFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        return flightService.getAllFlights(
                page,size,sortBy
        );
    }

    @GetMapping("/{flightNumber}")
    public FlightResponse getFlightByFlightNumber(@PathVariable String flightNumber){
        return flightService.getFlight(flightNumber);
    }

    @PostMapping
    public FlightResponse createFlight(@Valid @RequestBody FlightRequest request){
        return flightService.createFlight(request);
    }

    @DeleteMapping("/{flightNumber}")
    public void deleteFlight(@PathVariable String flightNumber){
        flightService.deleteFlight(flightNumber);
    }

    @GetMapping("/search")
    public Page<FlightResponse> searchFlights(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "departureTime") String sortBy
    ) {

        return flightService.searchFlights(
                from,
                to,
                date,
                page,
                size,
                sortBy
        );
    }


}
