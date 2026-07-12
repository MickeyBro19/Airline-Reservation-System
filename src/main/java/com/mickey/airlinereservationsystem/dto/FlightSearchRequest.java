package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FlightSearchRequest(
        @NotBlank
        String from,

        @NotBlank
        String to,

        @NotNull
        LocalDate date
) {
}
