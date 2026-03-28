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

    public synchronized BookingResponse createBooking(String header, BookingRequest request)
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
                saved.getSeats(),
                saved.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")),
                event.get().getTitle(),
                event.get().getPrice().multiply(BigDecimal.valueOf(saved.getSeats())),
                saved.getUser().getEmail()
        );
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

//    public List<Booking> getMyBookings(Long userId) {
//        return bookingRepository.findAllByUserId(userId);
//    }

    public List<BookingResponse> getMyBookings(String header) {
        String email = getEmailFromHeader(header);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotExistException("User not found"));

        List<Booking> userBookings = bookingRepository.findAllByUserId(user.getId());
        List<BookingResponse> bookingResponses = new ArrayList<>();

        userBookings.forEach(userBooking -> {
            BookingResponse bookingResponse = new BookingResponse(
                    userBooking.getId(),
                    userBooking.getSeats(),
                    userBooking.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")),
                    userBooking.getEvent().getTitle(),
                    userBooking.getEvent().getPrice().multiply(BigDecimal.valueOf(userBooking.getSeats())),
                    userBooking.getUser().getEmail());
            bookingResponses.add(bookingResponse);
        });

        return bookingResponses;
    }

    private String getEmailFromHeader(String header) {
        if (header.isEmpty() || !header.startsWith("Bearer ")) {
            throw new IncorrectDataException("Token invalid");
        }

        return jwtService.extractEmail(header.substring(7));
    }
}
