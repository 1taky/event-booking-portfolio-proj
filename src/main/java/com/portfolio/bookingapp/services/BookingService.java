package com.portfolio.bookingapp.services;



import com.portfolio.bookingapp.exeptions.IncorrectDataException;
import com.portfolio.bookingapp.models.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Booking> createBooking(String token, Booking booking) throws IncorrectDataException;
    Booking cancelBooking(Long id, Booking booking) throws IncorrectDataException;
    List<Booking> getMyBookings(Long userId);
}
