package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.exeptions.IncorrectDataException;
import com.portfolio.bookingapp.exeptions.NotExistException;
import com.portfolio.bookingapp.models.Booking;
import com.portfolio.bookingapp.models.Event;
import com.portfolio.bookingapp.models.User;
import com.portfolio.bookingapp.models.enums.BookingStatus;
import com.portfolio.bookingapp.repositories.BookingRepository;
import com.portfolio.bookingapp.repositories.EventRepository;
import com.portfolio.bookingapp.repositories.UserRepository;
import com.portfolio.bookingapp.security.JwtService;
import com.portfolio.bookingapp.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final JwtService jwtService;

    public ResponseEntity<Booking> createBooking(String token, Booking booking)
    throws IncorrectDataException {
        if (token.isEmpty() || token.startsWith("Bearer ")) {
            throw new IncorrectDataException("Token invalid");
        }
        String jwt = token.substring(7);
        String email = jwtService.extractEmail(jwt);

        Optional<Event> event = eventRepository.findById(booking.getEvent().getId());
        if (event.isPresent()) {
            if ((event.get().getAvailableSeats() >= booking.getSeats())) {
                event.get().setAvailableSeats(event.get().getAvailableSeats() - booking.getSeats());
                booking.setBookingStatus(BookingStatus.CONFIRMED);
            } else throw new IncorrectDataException("Incorrect number of seats");
        } else throw new NotExistException("Event not found");

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("User not found"));

        booking.setUser(user);
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);
        eventRepository.save(event.get());

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    public Booking cancelBooking(Long id, Booking booking)
            throws IncorrectDataException {
        Event event = eventRepository.findById(booking.getEvent().getId()).orElseThrow(
                () -> new NotExistException("Event not found"));

        if (bookingRepository.findById(id).isPresent()) {
            if (booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
                event.setAvailableSeats(event.getAvailableSeats() + booking.getSeats());
                booking.setBookingStatus(BookingStatus.CANCELLED);
                eventRepository.save(event);
            }
            else if(booking.getBookingStatus().equals(BookingStatus.CANCELLED))
                throw new IncorrectDataException("Booking is already cancelled");

        } else throw new IncorrectDataException("Incorrect booking id or status");

        return bookingRepository.save(booking);
    }

    public List<Booking> getMyBookings(Long userId) {
        return bookingRepository.findAllByUserId(userId);
    }
}
