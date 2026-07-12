package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Booking;
import com.mickey.airlinereservationsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Boolean existsByFlightAndSeatNumber(Flight flight, String seatNumber);
}
