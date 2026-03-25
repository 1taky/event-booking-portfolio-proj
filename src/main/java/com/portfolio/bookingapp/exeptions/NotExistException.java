package com.portfolio.bookingapp.exeptions;

public class NotExistException extends RuntimeException {
    public NotExistException(String message) {
        super(message);
    }
}
