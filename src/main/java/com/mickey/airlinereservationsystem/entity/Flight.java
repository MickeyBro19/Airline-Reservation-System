package com.mickey.airlinereservationsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    private String flightNumber;

    private String airline;

    @ManyToOne
    @JoinColumn(name="departure_airport_id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name="arrival_airport_id")
    private Airport arrivalAirport;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;


    private int totalSeats;
    private int availableSeats;
    private BigDecimal ticketPrice;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings;
}
