package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.dto.VenueRequest;
import com.portfolio.bookingapp.dto.VenueResponse;
import com.portfolio.bookingapp.models.Venue;
import com.portfolio.bookingapp.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venues")
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<List<Venue>> getVenues() {
        return new ResponseEntity<>(venueService.getAllVenues(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable Long id) {
        return new ResponseEntity<>(venueService.getVenueById(id),  HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VenueResponse> addVenue(@RequestBody VenueRequest request) {
        return new ResponseEntity<>(venueService.createVenue(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<VenueResponse> updateVenue(@PathVariable Long id, @RequestBody VenueRequest request) {
        return new ResponseEntity<>(venueService.updateVenue(id, request), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }
}
