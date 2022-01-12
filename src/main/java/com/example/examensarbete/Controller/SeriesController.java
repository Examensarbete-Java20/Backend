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

    @Autowired
    private SeriesService seriesService;


    @GetMapping("/title/{title}")
    public ResponseEntity<?> getID(@PathVariable String title){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getTitles(title));
        }
        catch(SeriesException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<?> getSeries(@PathVariable String imdb_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/all/{imdb_id}")
    public ResponseEntity<?> fetchSeries(@PathVariable String imdb_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.fetchTitle(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveSeries(@PathVariable String imdb_id, @PathVariable int counter){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getFiveSeries(imdb_id, counter));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }


    @GetMapping("/getseries/{imdb_id}")
    public ResponseEntity<?> getSeriesFromDB(@PathVariable String imdb_id){
        try {
            return ResponseEntity.ok(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PostMapping()
    public ResponseEntity<?> saveSeriesToDB(@RequestBody Series series){
        try {
            return ResponseEntity.ok(seriesService.saveSeries(series));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
    @PostMapping("/update/{rating}")
    public ResponseEntity<?> updateSeries(@RequestBody Series series, @PathVariable int rating){
        try {
            return ResponseEntity.ok(seriesService.updateSeriesRating(series,rating));
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

    @GetMapping("/getByIdBruw/{id}")
    public ResponseEntity<?> getThatone (@PathVariable String id){
        return ResponseEntity.ok(seriesService.getById(id));
    }
    }

