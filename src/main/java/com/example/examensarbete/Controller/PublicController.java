package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.RegExp;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.User;
import com.example.examensarbete.Service.MovieService;
import com.example.examensarbete.Service.SeriesService;
import com.example.examensarbete.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/public")
@Slf4j
public class PublicController {
    private RegExp regExp = new RegExp();

    private final MovieService movieService;
    private final SeriesService seriesService;
    private final UserService userService;



    /**
     * Methods for movies
     */

    @GetMapping("/movie/title/{title}")
    public ResponseEntity<?> getMovieId(@PathVariable String title) {
        if (regExp.inputValidation(title))
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getTitles(title));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/movie/{imdb_id}")
    public ResponseEntity<?> getMovie(@PathVariable String imdb_id) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieByImdbId(imdb_id));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/movie/all/{imdb_id}")
    public ResponseEntity<?> fetchMovie(@PathVariable String imdb_id) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(movieService.fetchTitle(imdb_id));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/movie/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveMovies(@PathVariable String imdb_id, @PathVariable int counter) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getFiveMovies(imdb_id, counter));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/movie/topTen")
    public ResponseEntity<?> getMovieTopTen() {
        return ResponseEntity.ok(movieService.getTopTen());
    }

    @PostMapping("/movie/update/{googleId}/{rating}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable String googleId, @PathVariable int rating) {
        if (regExp.inputValidation(googleId))
        return ResponseEntity.ok(movieService.updateMovieRating(movie, googleId, rating));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    /**
     * Methods for series
     */

    @GetMapping("/series/title/{title}")
    public ResponseEntity<?> getSeriesId(@PathVariable String title) {
        if (regExp.inputValidation(title))
        return ResponseEntity.status(HttpStatus.OK).body(seriesService.getTitles(title));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/series/{imdb_id}")
    public ResponseEntity<?> getSeries(@PathVariable String imdb_id) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(seriesService.getSeriesByImdbId(imdb_id));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/series/all/{imdb_id}")
    public ResponseEntity<?> fetchSeries(@PathVariable String imdb_id) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(seriesService.fetchTitle(imdb_id));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/series/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveSeries(@PathVariable String imdb_id, @PathVariable int counter) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.status(HttpStatus.OK).body(seriesService.getFiveSeries(imdb_id, counter));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/series/getseries/{imdb_id}")
    public ResponseEntity<?> getSeriesFromDB(@PathVariable String imdb_id) {
        if (regExp.inputValidation(imdb_id))
        return ResponseEntity.ok(seriesService.getSeriesByImdbId(imdb_id));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/series/topTen")
    public ResponseEntity<?> getSeriesTopTen() {
        return ResponseEntity.ok(seriesService.getTopTen());
    }

    @PostMapping("/series/update/{googleId}/{rating}")
    public ResponseEntity<?> updateSeries(@RequestBody Series series, @PathVariable String googleId, @PathVariable int rating) {
        if (regExp.inputValidation(googleId))
        return ResponseEntity.ok(seriesService.updateSeriesRating(series, googleId, rating));

        throw new SeriesException("NO SPECIAL CHARACTERS ALLOWED");
    }

    /**
     * Methods for user
     */


    @GetMapping("/{googleId}")
    public ResponseEntity<?> getUserByGoogleID(@PathVariable String googleId) {
        if (regExp.inputValidation(googleId))
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(googleId));

        throw new MovieException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/create/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
    }
}
