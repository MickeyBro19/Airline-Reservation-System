package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AirportRequest(
        @NotBlank
        String name,

        @NotBlank
        String city,

        @NotBlank
        String country,

        @Pattern(
                regexp = "^[A-Za-z]{3}$",
                message = "Airport code must contain exactly 3 letters"
        )
        @NotBlank
        String code
) {
}
