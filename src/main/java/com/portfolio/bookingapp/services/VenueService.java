package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.dto.VenueRequest;
import com.portfolio.bookingapp.dto.VenueResponse;
import com.portfolio.bookingapp.models.Venue;

import java.util.List;

public interface VenueService {
    VenueResponse createVenue(VenueRequest request);
    String updateVenue(Long id, VenueRequest request);
    List<Venue> getAllVenues();
    Venue getVenueById(long id);
    void deleteVenue(Long id);
}
