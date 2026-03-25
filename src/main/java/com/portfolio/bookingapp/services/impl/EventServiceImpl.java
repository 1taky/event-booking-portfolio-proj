package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.exeptions.NotExistException;
import com.portfolio.bookingapp.models.Event;
import com.portfolio.bookingapp.repositories.EventRepository;
import com.portfolio.bookingapp.repositories.VenueRepository;
import com.portfolio.bookingapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public ResponseEntity<Event> createEvent(Event event)
            throws NotExistException {
        if (venueRepository.findById(event.getVenue().getId()).isEmpty()) {
            throw new NotExistException("Venue not found");
        }
        eventRepository.save(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
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
