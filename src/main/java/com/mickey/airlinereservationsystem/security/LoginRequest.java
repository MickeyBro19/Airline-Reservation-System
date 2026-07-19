package com.mickey.airlinereservationsystem.security;

public record LoginRequest(
        String email,
        String password
        ) {
}
