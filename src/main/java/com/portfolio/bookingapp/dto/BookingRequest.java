package com.portfolio.bookingapp.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Integer seats;
    private Long eventId;
}
