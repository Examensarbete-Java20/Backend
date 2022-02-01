package com.example.examensarbete.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class CustomException {

    private final String message;
    private final HttpStatus status;
    private final int statusCode;
    private final ZonedDateTime timestamp;

    public CustomException(String message, HttpStatus status, int statusCode, ZonedDateTime timestamp) {

        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }
}
