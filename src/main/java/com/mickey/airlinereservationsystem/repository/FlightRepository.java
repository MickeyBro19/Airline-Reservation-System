package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByFlightNumber(String flightNumber);

    @Query(
            """
SELECT f 
from Flight f 
where f.departureAirport.code= :from
and f.arrivalAirport.code= :to
and f.departureTime between :start and :end
and f.availableSeats > 0
"""
    )
    Page<Flight> searchFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

}
