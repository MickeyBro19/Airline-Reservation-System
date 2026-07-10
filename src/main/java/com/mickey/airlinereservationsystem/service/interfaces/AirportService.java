package com.mickey.airlinereservationsystem.service.interfaces;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;
import org.springframework.data.domain.Page;

public interface AirportService {
     AirportResponse createAirport(AirportRequest request);
     AirportResponse getAirportByCode(String code);
     Page<AirportResponse> getAllAirport(int page, int size, String sortBy);
     void deleteAirport(String code);
}
