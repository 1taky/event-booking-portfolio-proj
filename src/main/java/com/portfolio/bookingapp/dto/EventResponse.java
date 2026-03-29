package com.portfolio.bookingapp.dto;

import com.portfolio.bookingapp.models.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventResponse {
    private String title;
    private String description;
    private Integer availableSeats;
    private BigDecimal price;
    private String dateTime;
    private Venue venue;
}
