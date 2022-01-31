package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
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

    @Autowired
    private MovieService movieService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;

    /**
     * Methods for movies
     */

    @GetMapping("/movie/title/{title}")
    public ResponseEntity<?> getMovieId(@PathVariable String title) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getTitles(title));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/movie/{imdb_id}")
    public ResponseEntity<?> getMovie(@PathVariable String imdb_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieByImdbId(imdb_id));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/movie/all/{imdb_id}")
    public ResponseEntity<?> fetchMovie(@PathVariable String imdb_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.fetchTitle(imdb_id));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/movie/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveMovies(@PathVariable String imdb_id, @PathVariable int counter) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getFiveMovies(imdb_id, counter));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/movie/topTen")
    public ResponseEntity<?> getMovieTopTen() {
        try {
            return ResponseEntity.ok(movieService.getTopTen());
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/movie/update/{googleId}/{rating}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable String googleId, @PathVariable int rating) {
        try {
            return ResponseEntity.ok(movieService.updateMovieRating(movie, googleId, rating));
        } catch (MovieException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * Methods for series
     */

    @GetMapping("/series/title/{title}")
    public ResponseEntity<?> getSeriesId(@PathVariable String title) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getTitles(title));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/series/{imdb_id}")
    public ResponseEntity<?> getSeries(@PathVariable String imdb_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/series/all/{imdb_id}")
    public ResponseEntity<?> fetchSeries(@PathVariable String imdb_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.fetchTitle(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/series/all/{imdb_id}/{counter}")
    public ResponseEntity<?> getFiveSeries(@PathVariable String imdb_id, @PathVariable int counter) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(seriesService.getFiveSeries(imdb_id, counter));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/series/getseries/{imdb_id}")
    public ResponseEntity<?> getSeriesFromDB(@PathVariable String imdb_id) {
        try {
            return ResponseEntity.ok(seriesService.getSeriesByImdbId(imdb_id));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/series/topTen")
    public ResponseEntity<?> getSeriesTopTen() {
        try {
            return ResponseEntity.ok(seriesService.getTopTen());
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/series/update/{googleId}/{rating}")
    public ResponseEntity<?> updateSeries(@RequestBody Series series, @PathVariable String googleId, @PathVariable int rating) {
        try {
            return ResponseEntity.ok(seriesService.updateSeriesRating(series, googleId, rating));
        } catch (SeriesException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * Methods for user
     */

    @GetMapping("/{googleId}")
    public ResponseEntity<?> getUserByGoogleID(@PathVariable String googleId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(googleId));
    }

    @PostMapping("/create/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info(String.valueOf(user));
        System.out.println("user: " + user);
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
    }
}
