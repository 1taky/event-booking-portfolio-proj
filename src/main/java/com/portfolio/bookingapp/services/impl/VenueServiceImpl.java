package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.dto.VenueRequest;
import com.portfolio.bookingapp.dto.VenueResponse;
import com.portfolio.bookingapp.exeptions.AlreadyExistException;
import com.portfolio.bookingapp.exeptions.NotExistException;
import com.portfolio.bookingapp.models.Venue;
import com.portfolio.bookingapp.repositories.VenueRepository;
import com.portfolio.bookingapp.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    // method to save place where event would be.
    // !! add duplicate check !!
    public VenueResponse createVenue(VenueRequest request) {
        Venue venue = getVenueFromRequest(request);

        Venue savedVenue = venueRepository.save(venue);

        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(savedVenue.getId());
        venueResponse.setName(savedVenue.getName());
        venueResponse.setAddress(savedVenue.getAddress());


        return venueResponse;
    }

    public VenueResponse updateVenue(Long id, VenueRequest request) {
        if(venueRepository.findById(id).isEmpty())
            throw new NotExistException("Venue not found");

        Venue savedVenue = venueRepository.save(getVenueFromRequest(request));

        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(savedVenue.getId());
        venueResponse.setName(savedVenue.getName());
        venueResponse.setAddress(savedVenue.getAddress());

        return venueResponse ;
    }

    public VenueResponse getVenueById(long id)
            throws IllegalArgumentException, NotExistException {
        if (id <= 0) throw new IllegalArgumentException("Incorrect parameter for Venue");
        Venue saved = venueRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Venue does not exist"));

        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(saved.getId());
        venueResponse.setName(saved.getName());
        venueResponse.setAddress(saved.getAddress());

        return venueResponse;
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public void deleteVenue(Long id) throws IllegalArgumentException, NotExistException {
        if (id <= 0)
            throw new IllegalArgumentException("Incorrect parameter for Venue");
        else if (venueRepository.findById(id).isEmpty())
            throw new NotExistException("Venue not found");

        venueRepository.deleteById(id);
    }

    private Venue getVenueFromRequest(VenueRequest request) {
        Venue venue = new Venue();
        venue.setName(request.getName());
        venue.setAddress(request.getAddress());
        return venue;
    }
}
