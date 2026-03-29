package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.dto.VenueRequest;
import com.portfolio.bookingapp.dto.VenueResponse;
import com.portfolio.bookingapp.models.Venue;

import java.util.List;

public interface VenueService {
    VenueResponse createVenue(VenueRequest request);
    VenueResponse updateVenue(Long id, VenueRequest request);
    List<Venue> getAllVenues();
    VenueResponse getVenueById(long id);
    void deleteVenue(Long id);
}
