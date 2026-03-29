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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

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

        if (eventRepository.getEventByTitleAndVenueId(event.getTitle(), event.getVenue().getId())) {
            throw new AlreadyExistException("Event already exists");
        }

        eventRepository.save(event);

        return new EventResponse(
                event.getTitle(),
                event.getDescription(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")),
                event.getVenue());
    }

    public Event updateEvent(Long id, Event event)
            throws NotExistException {
        if (eventRepository.findById(id).isEmpty() || venueRepository.findById(event.getVenue().getId()).isEmpty()) {
            throw new NotExistException("Venue not found");
        }

        return eventRepository.save(event);
    }

    public Event getEventById(long id) throws IllegalArgumentException, NotExistException {
        if (id <= 0) throw new IllegalArgumentException("Incorrect parameter for Event");

        return eventRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Event does not exist"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(Long id) throws IllegalArgumentException, NotExistException {
        if (id <= 0)
            throw new IllegalArgumentException("Incorrect parameter for Event");
        else if (eventRepository.findById(id).isEmpty())
            throw new NotExistException("Event not found");

        eventRepository.deleteById(id);
    }
}
