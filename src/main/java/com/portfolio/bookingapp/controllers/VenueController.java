package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.models.Venue;
import com.portfolio.bookingapp.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venues")
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public List<Venue> getVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("{id}")
    public Venue getVenue(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    @PostMapping
    public ResponseEntity<Venue> addVenue(@RequestBody Venue venue) {
        return venueService.createVenue(venue);
    }

    @PutMapping("{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }
}
