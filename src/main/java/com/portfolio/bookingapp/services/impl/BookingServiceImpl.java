package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.dto.BookingRequest;
import com.portfolio.bookingapp.dto.BookingResponse;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final JwtService jwtService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");

    public BookingResponse createBooking(String header, BookingRequest request)
    throws IncorrectDataException {
        String email = getEmailFromHeader(header);
        Booking booking = new Booking();

        Optional<Event> event = eventRepository.findById(request.getEventId());
        if (event.isPresent()) {
            if ((event.get().getAvailableSeats() >= request.getSeats())) {
                event.get().setAvailableSeats(event.get().getAvailableSeats() - request.getSeats());

                booking.setSeats(request.getSeats());
                booking.setEvent(event.get());
                booking.setBookingStatus(BookingStatus.CONFIRMED);
            } else throw new IncorrectDataException("Incorrect number of seats");
        } else throw new NotExistException("Event not found");

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistException("User not found"));

        booking.setUser(user);
        booking.setCreatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        eventRepository.save(event.get());

        return new BookingResponse(
                saved.getId(),
                saved.getBookingStatus(),
                saved.getSeats(),
                saved.getCreatedAt().format(formatter),
                event.get().getTitle(),
                event.get().getPrice().multiply(BigDecimal.valueOf(saved.getSeats())),
                saved.getUser().getEmail()
        );
    }

    public BookingResponse cancelBooking(Long id)
            throws IncorrectDataException {
        Booking found = bookingRepository.findById(id).orElseThrow(
                () -> new NotExistException("Booking not found"));
        Event event = eventRepository.findById(found.getEvent().getId()).orElseThrow(
                () -> new NotExistException("Event not found"));

            if (found.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
                event.setAvailableSeats(event.getAvailableSeats() + found.getSeats());
                found.setBookingStatus(BookingStatus.CANCELLED);
                eventRepository.save(event);
            }
            else if(found.getBookingStatus().equals(BookingStatus.CANCELLED))
                throw new IncorrectDataException("Booking is already cancelled");
            else
                throw new IncorrectDataException("Incorrect booking id or status");

        bookingRepository.save(found);
        return new BookingResponse(
                found.getId(),
                found.getBookingStatus(),
                found.getSeats(),
                found.getCreatedAt().format(formatter),
                found.getEvent().getTitle(),
                found.getEvent().getPrice().multiply(BigDecimal.valueOf(found.getSeats())),
                found.getUser().getEmail());
    }

    public List<BookingResponse> getMyBookings(String header) {
        String email = getEmailFromHeader(header);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotExistException("User not found"));

        List<Booking> userBookings = bookingRepository.findAllByUserId(user.getId());
        List<BookingResponse> bookingResponses = new ArrayList<>();

        userBookings.forEach(userBooking -> {
            BookingResponse bookingResponse = new BookingResponse(
                    userBooking.getId(),
                    userBooking.getBookingStatus(),
                    userBooking.getSeats(),
                    userBooking.getCreatedAt().format(formatter),
                    userBooking.getEvent().getTitle(),
                    userBooking.getEvent().getPrice().multiply(BigDecimal.valueOf(userBooking.getSeats())),
                    userBooking.getUser().getEmail());
            bookingResponses.add(bookingResponse);
        });

        return bookingResponses;
    }

    private String getEmailFromHeader(String header) {
        if (!header.startsWith("Bearer ")) {
            throw new IncorrectDataException("Token invalid");
        }

        return jwtService.extractEmail(header.substring(7));
    }
}
