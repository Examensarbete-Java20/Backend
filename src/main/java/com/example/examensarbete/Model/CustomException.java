package com.example.examensarbete.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * Created by Christoffer Grännby
 * Date: 2021-12-17
 * Time: 12:04
 * Project: Backend
 * Copyright: MIT
 */
@AllArgsConstructor
@Builder
@Value
public class CustomException {
    String message;
    HttpStatus status;
    int statusCode;
}
