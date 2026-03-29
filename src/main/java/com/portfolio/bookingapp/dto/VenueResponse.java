package com.portfolio.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VenueResponse {
    private Long id;
    private String name;
    private String address;
}
