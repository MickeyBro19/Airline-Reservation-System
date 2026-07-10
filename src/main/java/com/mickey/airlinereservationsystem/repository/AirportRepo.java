package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepo extends JpaRepository<Airport,Long> {
    Boolean existsByCode(String code);
}
