package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.dto.EventRequest;
import com.portfolio.bookingapp.dto.EventResponse;
import com.portfolio.bookingapp.exeptions.AlreadyExistException;
import com.portfolio.bookingapp.exeptions.NotExistException;
import com.portfolio.bookingapp.models.Event;
import com.portfolio.bookingapp.models.Venue;
import com.portfolio.bookingapp.repositories.EventRepository;
import com.portfolio.bookingapp.repositories.VenueRepository;
import com.portfolio.bookingapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");

    public EventResponse createEvent(EventRequest request)
            throws NotExistException {
        if (venueRepository.findById(request.getVenueId()).isEmpty()) {
            throw new NotExistException("Venue not found");
        }

        Optional<Venue> venue = venueRepository.findById(request.getVenueId());

        Event event = new Event();

        if (venue.isPresent()) {
            event.setTitle(request.getTitle());
            event.setDescription(request.getDescription());
            event.setPrice(request.getPrice());
            event.setDateTime(request.getDateTime());
            event.setAvailableSeats(request.getAvailableSeats());
            event.setVenue(venue.get());
        }

        if (eventRepository.existsByTitleAndVenueId(event.getTitle(), event.getVenue().getId())) {
            throw new AlreadyExistException("Event already exists");
        }

        eventRepository.save(event);

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")),
                event.getVenue());
    }

    public EventResponse updateEvent(Long id, EventRequest request)
            throws NotExistException {
        if (eventRepository.findById(id).isEmpty() || venueRepository.findById(request.getVenueId()).isEmpty()) {
            throw new NotExistException("Venue not found");
        }

        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setPrice(request.getPrice());
        event.setDateTime(request.getDateTime());
        event.setAvailableSeats(request.getAvailableSeats());
        event.setVenue(venueRepository.findById(request.getVenueId()).get());

        eventRepository.save(event);

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getDateTime().format(formatter),
                event.getVenue()
        );
    }

    public EventResponse getEventById(long id) throws IllegalArgumentException, NotExistException {
        if (id <= 0) throw new IllegalArgumentException("Incorrect parameter for Event");

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Event does not exist"));

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getDateTime().format(formatter),
                event.getVenue()
        );
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventResponse> response = new ArrayList<>();

        events.forEach(event -> response.add(new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getDateTime().format(formatter),
                event.getVenue()
        )));

        return response;
    }

    public void deleteEvent(Long id) throws IllegalArgumentException, NotExistException {
        if (id <= 0)
            throw new IllegalArgumentException("Incorrect parameter for Event");
        else if (eventRepository.findById(id).isEmpty())
            throw new NotExistException("Event not found");

        eventRepository.deleteById(id);
    }
}
