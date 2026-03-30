package com.portfolio.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventRequest {
    private String title;
    private String description;
    private Integer availableSeats;
    private BigDecimal price;
    private LocalDateTime dateTime;
    private Long venueId;
}
