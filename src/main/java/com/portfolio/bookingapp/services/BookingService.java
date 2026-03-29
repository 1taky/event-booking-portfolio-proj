package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.dto.BookingRequest;
import com.portfolio.bookingapp.dto.BookingResponse;
import com.portfolio.bookingapp.exeptions.IncorrectDataException;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(String token, BookingRequest booking) throws IncorrectDataException;
    BookingResponse cancelBooking(Long id, BookingRequest booking) throws IncorrectDataException;
    List<BookingResponse> getMyBookings(String header);
}
