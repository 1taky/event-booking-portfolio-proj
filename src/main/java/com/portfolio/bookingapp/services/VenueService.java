package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.models.Venue;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VenueService {
    ResponseEntity<Venue> createVenue(Venue venue);
    Venue updateVenue(Long id, Venue venue);
    List<Venue> getAllVenues();
    Venue getVenueById(long id);
    void deleteVenue(Long id);
}
