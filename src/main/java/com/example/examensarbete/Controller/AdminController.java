package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Service.MovieService;
import com.example.examensarbete.Service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final MovieService movieService;
    private final SeriesService seriesService;

    @PostMapping("/save/movie")
    public ResponseEntity<?> saveMovieToDB(@RequestBody Movie movie) {
            return ResponseEntity.ok(movieService.saveMovie(movie));
    }

    @PostMapping("/save/serie")
    public ResponseEntity<?> saveSeriesToDB(@RequestBody Series series){
            return ResponseEntity.ok(seriesService.saveSeries(series));
    }
}
