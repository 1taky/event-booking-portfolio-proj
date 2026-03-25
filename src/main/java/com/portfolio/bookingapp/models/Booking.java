package com.portfolio.bookingapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.bookingapp.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int seats;
    LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;
}
