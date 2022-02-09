package com.example.examensarbete.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<Object> handleMovieException(MovieException movieException) {
        CustomException customException = new CustomException(movieException.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(ZoneId.of("Z"))); //CET för svensk tid
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeriesException.class)
    public ResponseEntity<Object> handleSeriesException(SeriesException seriesException) {
        CustomException customException = new CustomException(seriesException.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(ZoneId.of("Z"))); //CET för svensk tid
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException userException) {
        CustomException customException = new CustomException(userException.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(ZoneId.of("Z"))); //CET för svensk tid
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WatchListException.class)
    public ResponseEntity<Object> handleWatchListException(WatchListException watchListException) {
        CustomException customException = new CustomException(watchListException.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(ZoneId.of("Z"))); //CET för svensk tid
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }
}
