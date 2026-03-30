package com.portfolio.bookingapp.dto;

import com.portfolio.bookingapp.models.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private Integer availableSeats;
    private BigDecimal price;
    private String dateTime;
    private Venue venue;
}
