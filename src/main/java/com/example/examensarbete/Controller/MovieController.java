package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Service.MovieService;
import com.example.examensarbete.Exception.MovieException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getID(@PathVariable String title){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getTitles(title));
        }
        catch(MovieException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<?> getMovie(@PathVariable String imdb_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieByImdbId(imdb_id));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveMovieToDB(@RequestBody Movie movie){
        try {
            return ResponseEntity.ok(movieService.saveMovie(movie));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }
    @PostMapping("/update/{rating}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable int rating){
        try {
            return ResponseEntity.ok(movieService.updateMovieRating(movie,rating));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/topTen")
    public ResponseEntity<?> getTopTen(){
        try {
            return ResponseEntity.ok(movieService.getTopTen());
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
