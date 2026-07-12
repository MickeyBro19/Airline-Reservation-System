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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository  flightRepository;
    private final AirportRepository airportRepository;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        String flightNumber =
                normalizeAirportCode(request.flightNumber());

        if(flightRepository.existsByFlightNumber(flightNumber)) {
            throw new RuntimeException("Flight Already Exists");
        }

        Airport departureAirport = getAirport(request.departureAirportCode());
        Airport arrivalAirport = getAirport(request.arrivalAirportCode());

        if(departureAirport.equals(arrivalAirport)) throw new RuntimeException("Departure and arrival can't be same");

        if (!request.departureTime().isBefore(request.arrivalTime())) {
            throw new RuntimeException("Departure time must be before arrival time.");
        }

        Flight flight=Flight.builder()
                .flightNumber(normalizeAirportCode(request.flightNumber()))
                .airline(request.airline())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .totalSeats(request.totalSeats())
                .availableSeats(request.totalSeats())
                .ticketPrice(request.ticketPrice())
                .build();

        Flight savedFlight = flightRepository.save(flight);

        return mapToResponse(savedFlight);    }

    @Override
    public FlightResponse getFlight(String flightNumber) {
            Flight flight=flightRepository.findByFlightNumber(normalizeAirportCode(flightNumber))
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
        Flight flight=flightRepository.findByFlightNumber(normalizeAirportCode(flightNumber))
                .orElseThrow(()->new RuntimeException("Flight not exist"));
        flightRepository.delete(flight);
    }

    @Override
    public Page<FlightResponse> searchFlights(String from, String to, LocalDate date, int page, int size, String sortBy) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy)
                );
        Page<Flight> flights =
                flightRepository.searchFlights(
                        normalizeAirportCode(from),
                        normalizeAirportCode(to),
                        start,
                        end,
                        pageable
                );
        return flights.map(this::mapToResponse);
    }


    private FlightResponse mapToResponse(Flight flight){
        return new FlightResponse(
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

    private Airport getAirport(String code) {

        return airportRepository.findByCode(
                normalizeAirportCode(code)
        ).orElseThrow(() ->
                new RuntimeException("Airport not found"));
    }

    private String normalizeAirportCode(String number){return number.trim().toUpperCase();}

}
