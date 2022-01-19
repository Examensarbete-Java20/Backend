package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all/{imdb_id}")
    public ResponseEntity<?> fetchMovie(@PathVariable String imdb_id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.fetchTitle(imdb_id));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveMovies(@PathVariable String imdb_id, @PathVariable int counter){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getFiveMovies(imdb_id, counter));
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
    @PostMapping("/update/{googleId}/{rating}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable String googleId, @PathVariable int rating){
        try {
            return ResponseEntity.ok(movieService.updateMovieRating(movie, googleId, rating));
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

    @GetMapping("/getByIdBruw/{id}")
    public ResponseEntity<?> getThatone (@PathVariable String id){
        return ResponseEntity.ok(movieService.getById(id));
    }
}
