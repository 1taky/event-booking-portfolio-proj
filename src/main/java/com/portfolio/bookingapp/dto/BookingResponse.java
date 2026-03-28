package com.portfolio.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Integer seats;
    private String createdAt;
    private String eventTitle;
    private BigDecimal totalPrice;
    private String userEmail;
}
