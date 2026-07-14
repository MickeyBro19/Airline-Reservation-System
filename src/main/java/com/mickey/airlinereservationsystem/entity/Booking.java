package com.mickey.airlinereservationsystem.entity;

import com.mickey.airlinereservationsystem.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private LocalDateTime bookingDate;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @OneToOne(mappedBy="booking",
            cascade = CascadeType.ALL)
    private Payment payment;

    private String bookingReference;

}
