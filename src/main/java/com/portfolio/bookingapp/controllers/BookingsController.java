package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.dto.BookingRequest;
import com.portfolio.bookingapp.dto.BookingResponse;
import com.portfolio.bookingapp.services.BookingService;
import com.portfolio.bookingapp.services.impl.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingsController {
    private final BookingService bookingService;
    private final MailSenderService mailSenderService;

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(@RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(bookingService.getMyBookings(header), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestHeader("Authorization") String header, @RequestBody BookingRequest booking) {
        BookingResponse response = bookingService.createBooking(header, booking);
        String htmlContent = String.format("""
    <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #eeeeee; border-radius: 8px; overflow: hidden;">
        <div style="background-color: #2c3e50; color: #ffffff; padding: 20px; text-align: center;">
            <h2 style="margin: 0;">Ваше бронювання #%d</h2>
        </div>
        
        <div style="padding: 30px; color: #333333;">
            <p style="font-size: 18px;">Ваш квиток на захід: <strong style="color: #2c3e50;">"%s"</strong></p>
            
            <div style="background-color: #f8f9fa; border-left: 4px solid #2c3e50; padding: 15px; margin: 20px 0;">
                <p style="margin: 5px 0;"><strong>Час бронювання:</strong> %s</p>
                <p style="margin: 5px 0;"><strong>Кількість місць:</strong> %d</p>
                <p style="margin: 5px 0;"><strong>Статус:</strong> <span style="color: #27ae60;">%s</span></p>
                <p style="margin: 5px 0; font-size: 18px;"><strong>До сплати:</strong> %.2f ₴</p>
            </div>
            
            <p>Дякую, що користуєтесь сервісом!</p>
        </div>
        
        <div style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 12px; color: #7f8c8d;">
            Сподіваюсь Вам сподобався проєктік😊
        </div>
    </div>
    """,
                response.getId(),
                response.getEventTitle(),
                response.getCreatedAt(),
                response.getSeats(),
                response.getBookingStatus(),
                response.getTotalPrice()
        );
        mailSenderService.sendMail(
                response.getUserEmail(),
                "Бронювання №" + response.getId(),
                htmlContent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        BookingResponse response = bookingService.cancelBooking(id);
        String htmlContent = String.format("""
    <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #eeeeee; border-radius: 8px; overflow: hidden;">
        <div style="background-color: #2c3e50; color: #ffffff; padding: 20px; text-align: center;">
            <h2 style="margin: 0;">Скасування бронювання #%d</h2>
        </div>
        
        <div style="padding: 30px; color: #333333;">
            <p style="font-size: 18px;">Скасування Вашого квитка на захід: <strong style="color: #2c3e50;">"%s"</strong></p>
            
            <div style="background-color: #f8f9fa; border-left: 4px solid #2c3e50; padding: 15px; margin: 20px 0;">
                <p style="margin: 5px 0;"><strong>Час бронювання:</strong> %s</p>
                <p style="margin: 5px 0;"><strong>Кількість місць:</strong> %d</p>
                <p style="margin: 5px 0;"><strong>Статус:</strong> <span style="color: #ae2727;">%s</span></p>
                <p style="margin: 5px 0; font-size: 18px;"><strong>До сплати:</strong> %.2f ₴</p>
            </div>
            
            <p>Невдовзі кошти повернуться на Ваш баланс.</p>
            <p>Дякую, що користуєтесь сервісом!</p>
        </div>
        
        <div style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 12px; color: #7f8c8d;">
            Сподіваюсь Вам сподобався проєктік😊
        </div>
    </div>
    """,
                response.getId(),
                response.getEventTitle(),
                response.getCreatedAt(),
                response.getSeats(),
                response.getBookingStatus(),
                response.getTotalPrice()
        );

        mailSenderService.sendMail(
                response.getUserEmail(),
                "Скасування білета",
                htmlContent
        );

        return new ResponseEntity<>(response,  HttpStatus.OK);
    }
}
