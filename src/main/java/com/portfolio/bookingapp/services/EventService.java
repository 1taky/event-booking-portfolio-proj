package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.dto.EventRequest;
import com.portfolio.bookingapp.dto.EventResponse;
import com.portfolio.bookingapp.models.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest event);
    Event updateEvent(Long id, Event event);
    List<Event> getAllEvents();
    Event getEventById(long id);
    void deleteEvent(Long id);
}
