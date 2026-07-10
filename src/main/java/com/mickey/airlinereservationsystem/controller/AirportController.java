package com.mickey.airlinereservationsystem.controller;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;
import com.mickey.airlinereservationsystem.service.interfaces.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airport")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping
    public Page<AirportResponse> getAllAirports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        return airportService.getAllAirports(
                page,size,sortBy
        );
    }

    @GetMapping("/{code}")
    public AirportResponse getAirportByCode(@PathVariable String code){
        return airportService.getAirportByCode(code);
    }

    @PostMapping
    public AirportResponse createAirport(@Valid @RequestBody AirportRequest request){
        return airportService.createAirport(request);
    }

    @DeleteMapping("/{code}")
    public void deleteAirport(@PathVariable String code){
        airportService.deleteAirport(code);
    }
}
