package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.models.Booking;
import com.portfolio.bookingapp.models.User;

import com.portfolio.bookingapp.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingsController {
    private final BookingService bookingService;

    @GetMapping("/my")
    public List<Booking> getMyBookings() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookingService.getMyBookings(user.getId());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestHeader("Authorization") String header, @RequestBody Booking booking) {
        return bookingService.createBooking(header, booking);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id,  @RequestBody Booking booking) {
        bookingService.cancelBooking(id,  booking);
        return ResponseEntity.noContent().build();
    }
}
