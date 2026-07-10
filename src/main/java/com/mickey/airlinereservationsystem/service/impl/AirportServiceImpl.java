package com.mickey.airlinereservationsystem.service.impl;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;
import com.mickey.airlinereservationsystem.entity.Airport;
import com.mickey.airlinereservationsystem.repository.AirportRepository;
import com.mickey.airlinereservationsystem.service.interfaces.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository repo;


    @Override
    public AirportResponse createAirport(AirportRequest request) {
        String code=request.code().trim().toUpperCase();
        if(repo.existsByCode(code)){
            throw new RuntimeException("Airport Already Exists");
        }
        Airport airport=Airport.builder()
                .name(request.name())
                .city(request.city())
                .country(request.country())
                .code(code)
                .build();
        return mapToResponse(repo.save(airport));

    }

    @Override
    public AirportResponse getAirportByCode(String code) {
        Airport airport = repo.findByCode(code.trim().toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Airport not found"));
        return mapToResponse(airport);
    }

    @Override
    public Page<AirportResponse> getAllAirports(
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        Page<Airport> airports = repo.findAll(pageable);

        return airports.map(this::mapToResponse);
    }


    @Override
    public void deleteAirport(String code) {
        Airport airport = repo.findByCode(code.trim().toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Airport not found"));
        repo.delete(airport);

    }

    private AirportResponse mapToResponse(Airport airport){
        return new AirportResponse(
                airport.getId(),
                airport.getCode(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry()
        );
    }
}
