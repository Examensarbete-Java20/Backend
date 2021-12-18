package com.example.examensarbete.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * A class with a custommade exception
 */

@AllArgsConstructor
@Builder
@Value
public class CustomException {
    String message;
    HttpStatus status;
    int statusCode;
}
