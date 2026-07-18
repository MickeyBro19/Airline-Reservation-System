package com.mickey.airlinereservationsystem.service.impl;

import com.mickey.airlinereservationsystem.dto.BookingRequest;
import com.mickey.airlinereservationsystem.dto.BookingResponse;
import com.mickey.airlinereservationsystem.entity.Booking;
import com.mickey.airlinereservationsystem.entity.Flight;
import com.mickey.airlinereservationsystem.entity.Payment;
import com.mickey.airlinereservationsystem.entity.User;
import com.mickey.airlinereservationsystem.enums.PaymentStatus;
import com.mickey.airlinereservationsystem.enums.TicketStatus;
import com.mickey.airlinereservationsystem.repository.BookingRepository;
import com.mickey.airlinereservationsystem.repository.FlightRepository;
import com.mickey.airlinereservationsystem.repository.UserRepository;
import com.mickey.airlinereservationsystem.service.interfaces.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    @Override
    @Transactional
    public BookingResponse bookFlight(BookingRequest request) {

        User user=userRepository.findById(request.userId())
                .orElseThrow(()->new RuntimeException("User not found"));
        Flight flight= flightRepository.findByFlightNumber(normalize(request.flightNumber()))
                .orElseThrow(()->new RuntimeException("Flight Not Found"));
        if(!flight.getDepartureTime().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Flight has already departed.");
        }
        if(bookingRepository.existsByFlightAndSeatNumber(flight,normalize(request.seatNumber()))) throw new RuntimeException("Already booked");

        try{
            flight.reserveSeat();
            String bookingReference= getBookingReference(flight.getAirline(),request.flightNumber());
            LocalDateTime date=LocalDateTime.now();


            Booking booking=Booking.builder()
                    .user(user)
                    .flight(flight)
                    .bookingDate(date)
                    .seatNumber(normalize(request.seatNumber()))
                    .ticketStatus(TicketStatus.BOOKED)
                    .bookingReference(bookingReference)
                    .build();

            Payment payment=Payment
                    .builder()
                    .booking(booking)
                    .amount(flight.getTicketPrice())
                    .paymentStatus(PaymentStatus.SUCCESS)
                    .paymentDate(date)
                    .build();
            booking.setPayment(payment);

            return mapToResponse(bookingRepository.save(booking));
        }
        catch(ObjectOptimisticLockingFailureException ex){

            throw new RuntimeException(
                    "Another passenger booked this seat. Please try again."
            );

        }


    }



    private String getBookingReference(String airline, String flightNumber) {
        int year=LocalDate.now().getYear();
        return airline.toUpperCase()
                + "-"
                + year
                + "-"
                + normalize(flightNumber)
                + "-"
                + UUID.randomUUID();
    }

    private BookingResponse mapToResponse(Booking booking){
        return  new BookingResponse(
                booking.getBookingReference(),
                booking.getUser().getName(),
                normalize(booking.getFlight().getFlightNumber()),
                normalize(booking.getSeatNumber()),
                booking.getTicketStatus()

        );
    }
    private String normalize(String number){return number.trim().toUpperCase();}

}
