package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.dto.EventRequest;
import com.portfolio.bookingapp.dto.EventResponse;
import com.portfolio.bookingapp.models.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest event);
    EventResponse updateEvent(Long id, EventRequest event);
    List<EventResponse> getAllEvents();
    EventResponse getEventById(long id);
    void deleteEvent(Long id);
}
