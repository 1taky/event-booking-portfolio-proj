package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.dto.BookingRequest;
import com.portfolio.bookingapp.dto.BookingResponse;
import com.portfolio.bookingapp.models.Booking;
import com.portfolio.bookingapp.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingsController {
    private final BookingService bookingService;

    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(@RequestHeader("Authorization") String header) {
        return bookingService.getMyBookings(header);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestHeader("Authorization") String header, @RequestBody BookingRequest booking) {
        return new ResponseEntity<>(bookingService.createBooking(header, booking), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id,  @RequestBody Booking booking) {
        bookingService.cancelBooking(id,  booking);
        return ResponseEntity.noContent().build();
    }
}
