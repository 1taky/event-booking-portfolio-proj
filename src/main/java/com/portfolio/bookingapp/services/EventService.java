package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.models.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<Event> createEvent(Event event);
    Event updateEvent(Long id, Event event);
    List<Event> getAllEvents();
    Event getEventById(long id);
    void deleteEvent(Long id);
}
