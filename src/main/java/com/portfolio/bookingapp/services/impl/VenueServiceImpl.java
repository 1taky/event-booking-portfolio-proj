package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.exeptions.IncorrectDataException;
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

    public ResponseEntity<Venue> createVenue(Venue venue) {
        venueRepository.save(venue);
        return new ResponseEntity<>(venue, HttpStatus.CREATED);
    }

    public Venue updateVenue(Long id, Venue venue) {
        if(venueRepository.findById(id).isEmpty())
            throw new NotExistException("Venue not found");

        return venueRepository.save(venue);
    }

    public Venue getVenueById(long id)
            throws IllegalArgumentException, NotExistException {
        if (id <= 0) throw new IllegalArgumentException("Incorrect parameter for Venue");
        return venueRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Venue does not exist"));
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
}
