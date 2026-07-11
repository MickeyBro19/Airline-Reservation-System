package com.mickey.airlinereservationsystem.service.impl;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import com.mickey.airlinereservationsystem.entity.Airport;
import com.mickey.airlinereservationsystem.entity.Flight;
import com.mickey.airlinereservationsystem.repository.AirportRepository;
import com.mickey.airlinereservationsystem.repository.FlightRepository;
import com.mickey.airlinereservationsystem.service.interfaces.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository  flightRepository;
    private final AirportRepository airportRepository;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        if(flightRepository.existsByFlightNumber(request.flightNumber())){
            throw new RuntimeException("Flight Already Exists");
        }
        Airport departureAirport = airportRepository.findByCode(codeNormalization(request.departureAirportCode()))
                .orElseThrow(() ->
                        new RuntimeException("Airport not found"));
        Airport arrivalAirport = airportRepository.findByCode(codeNormalization(request.arrivalAirportCode()))
                .orElseThrow(() ->
                        new RuntimeException("Airport not found"));
        if(departureAirport.equals(arrivalAirport)) throw new RuntimeException("Departure and arrival can't be same");
        LocalDateTime departureTime=request.departureTime();
        LocalDateTime arrivalTime=request.arrivalTime();
        if(!departureTime.isBefore(arrivalTime)) throw new RuntimeException("Error time mismatch");

        Flight flight=Flight.builder()
                .flightNumber(normalizeFlightNumber(request.flightNumber()))
                .airline(request.airline())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .totalSeats(request.totalSeats())
                .availableSeats(request.totalSeats())
                .ticketPrice(request.ticketPrice())
                .build();

        Flight savedFlight = flightRepository.save(flight);

        return mapToResponse(savedFlight);    }

    @Override
    public FlightResponse getFlight(String flightNumber) {
            Flight flight=flightRepository.findByFlightNumber(normalizeFlightNumber(flightNumber))
                    .orElseThrow(()->new RuntimeException("Flight not exist"));
            return mapToResponse(flight);
    }

    @Override
    public Page<FlightResponse> getAllFlights(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        Page<Flight> flights = flightRepository.findAll(pageable);

        return flights.map(this::mapToResponse);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        Flight flight=flightRepository.findByFlightNumber(normalizeFlightNumber(flightNumber))
                .orElseThrow(()->new RuntimeException("Flight not exist"));
        flightRepository.delete(flight);
    }

    private FlightResponse mapToResponse(Flight flight){
        return new FlightResponse(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getDepartureAirport().getCode(),
                flight.getArrivalAirport().getCode(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getTicketPrice(),
                flight.getAvailableSeats()

        );
    }

    private String codeNormalization(String code){
        return code.trim().toUpperCase();
    }
    private String normalizeFlightNumber(String number){return number.trim().toUpperCase();}

}
