package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.dto.VenueRequest;
import com.portfolio.bookingapp.dto.VenueResponse;
import com.portfolio.bookingapp.models.Venue;
import com.portfolio.bookingapp.services.VenueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VenueControllerTest {
    @Mock
    private VenueService venueService;

    @InjectMocks
    private VenueController venueController;

    @Test
    void getVenuesTest() {
        List<Venue> venues = List.of(
                new Venue(1L, "Хрещатик", "ст. м. Хрещатик"),
                new Venue(2L, "ВДНГ", "ст. м. Виставковий центр")
        );

        when(venueService.getAllVenues()).thenReturn(venues);

        assertEquals(new ResponseEntity<>(venues, HttpStatus.OK), venueController.getVenues());
    }

    @Test
    void getVenueTest() {
        List<Venue> venues = List.of(
                new Venue(1L, "Хрещатик", "ст. м. Хрещатик"),
                new Venue(2L, "ВДНГ", "ст. м. Виставковий центр")
        );

        Venue venue = venues.get(0);

        when(venueService.getVenueById(1L)).thenReturn(new VenueResponse(venue.getId(), venue.getName(), venue.getAddress()));

        assertEquals(new ResponseEntity<>(new VenueResponse(venue.getId(), venue.getName(), venue.getAddress()), HttpStatus.OK), venueController.getVenue(1L));
    }

    @Test
    void addVenueTest() {
        VenueRequest venueRequest = new VenueRequest();
        venueRequest.setName("ВДНГ");
        venueRequest.setAddress("ст. м. Виставковий центр");

        when(venueService.createVenue(venueRequest)).thenReturn(new VenueResponse(1L, venueRequest.getName(), venueRequest.getAddress()));

        assertEquals(new ResponseEntity<>(new VenueResponse(1L, venueRequest.getName(), venueRequest.getAddress()), HttpStatus.CREATED), venueController.createVenue(venueRequest));
    }

    @Test
    void updateVenueTest() {
        Long id = 1L;

        VenueRequest venueRequest = new VenueRequest();
        venueRequest.setName("ВДНГ");
        venueRequest.setAddress("ст. м. Виставковий центр");

        when(venueService.updateVenue(id, venueRequest)).thenReturn(new VenueResponse(1L, venueRequest.getName(), venueRequest.getAddress()));

        assertEquals(new ResponseEntity<>(new VenueResponse(1L, "ВДНГ", "ст. м. Виставковий центр"),  HttpStatus.OK), venueController.updateVenue(id,venueRequest));
    }
}