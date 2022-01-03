package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Service.MovieAndSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/all")
public class MovieAndSeriesController {

    @Autowired
    private MovieAndSeriesService movieAndSeriesService;

    //TODO: Skapa funktionalitet för att hämta både film å serier

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getID(@PathVariable String title){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieAndSeriesService.fetchTitle(title));
        }
        catch(SeriesException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

}
