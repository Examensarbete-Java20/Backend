package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/series")
public class SeriesController {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    @Autowired
    private SeriesService seriesService;


    @GetMapping("/title/{title}")
    public ResponseEntity<?> getID(@PathVariable String title){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.fetchTitle(title));
        }
        catch(SeriesException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<?> getMovie(@PathVariable String imdb_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }


    @GetMapping("/getmovie/{imdb_id}")
    public ResponseEntity<?> getMovieFromDB(@PathVariable String imdb_id){
        try {
            return ResponseEntity.ok(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PostMapping()
    public ResponseEntity<?> saveMovieToDB(@RequestBody Series series){
        try {
            return ResponseEntity.ok(seriesService.saveSeries(series));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
    @PostMapping("/update")
    public ResponseEntity<?> updateMovie(@RequestBody Series series){
        try {
            return ResponseEntity.ok(seriesService.updateSeries(series));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/topTen")
    public ResponseEntity<?> getTopTen(){
        try {
            return ResponseEntity.ok(seriesService.getTopTen());
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    }

