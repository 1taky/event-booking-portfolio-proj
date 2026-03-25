package com.portfolio.bookingapp.exeptions.handler;

import com.portfolio.bookingapp.exeptions.IncorrectDataException;
import com.portfolio.bookingapp.exeptions.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeptionHandler{
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectDataException(IncorrectDataException ex) {
        Map<String,String> err = new HashMap<>();

        err.put("error", "Incorrect data");
        err.put("message", ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<Map<String, String>> handleNotExistException(NotExistException ex) {
        Map<String,String> err = new HashMap<>();

        err.put("error", "Not exist");
        err.put("message", ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
