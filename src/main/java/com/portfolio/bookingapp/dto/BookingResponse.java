package com.portfolio.bookingapp.dto;

import com.portfolio.bookingapp.models.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private BookingStatus bookingStatus;
    private Integer seats;
    private String createdAt;
    private String eventTitle;
    private BigDecimal totalPrice;
    private String userEmail;
}
