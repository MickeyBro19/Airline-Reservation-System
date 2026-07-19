package com.mickey.airlinereservationsystem.security;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
