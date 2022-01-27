package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Exception.MovieException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    private RapidApiMethods rapid = new RapidApiMethods();

    @Autowired
    private MovieRepository movieRepository;


    public List<Title> getTitles(String title) {
        List<Title> output = rapid.getTitles(title, rapidApiKey, rapid.MOVIE).stream().limit(5).collect(Collectors.toList());

        if (output.size() == 0)
            throw new MovieException(HttpStatus.NOT_FOUND + "  Cant find anything on that title");

        return output;
    }

    public Movie getMovieByImdbId(String imdbId) {
        Movie output = rapid.getMovieByImdbId(imdbId, rapidApiKey, movieRepository);

        if (output == null)
            throw new MovieException(HttpStatus.NOT_FOUND + " Movie not found");

        return output;
    }

    public Movie saveMovie(Movie movie) {
        Movie temp = movieRepository.getByImdbId(movie.getImdbId());

        if (temp != null)
            throw new MovieException(HttpStatus.BAD_REQUEST + " Movie already exists in database");

        movie.setExist(true);
        return movieRepository.save(movie);
    }

    public Movie updateMovieRating(Movie movie, String googleId, int rating) {
        int oldRating = 0;
        int addVote = 1;
        String vote = googleId + ", " + rating;
        if (movie.getVoters() == null){
            List<String> list = new ArrayList<>();
            list.add(vote);
            movie.setVoters(list);
        } else {
            for (String s: movie.getVoters()) {
                if (s.contains(googleId)) {
                    oldRating = Integer.parseInt(s.substring(s.indexOf(" ")+1));
                    addVote = 0;
                    movie.getVoters().remove(s);
                    movie.getVoters().add(vote);
                    break;
                }
            }
        }


        movie.setTotalRating(movie.getTotalRating() + rating - oldRating);
        movie.setTotalOfVoters(movie.getTotalOfVoters() + addVote);
        if (!movie.isExist())
            movie.setExist(true);

        movie.setOwnRating(movie.getTotalRating() / movie.getTotalOfVoters());
        return movieRepository.save(movie);
    }

    public List<Movie> getTopTen() {
        List<Movie> temp = movieRepository.findAll();
        temp.sort((movie1, movie2) -> Double.compare(movie2.getOwnRating(), movie1.getOwnRating()));

        return temp.stream().limit(10).collect(Collectors.toList());
    }

    public List<Movie> fetchTitle(String title){
        List<Movie> output = new ArrayList<>();
        List<Title> movieResult = rapid.getTitles(title, rapidApiKey, rapid.MOVIE);

        movieResult.forEach(movie -> {
            Movie tempMovie = rapid.getMovieByImdbId(movie.getImdb_id(), rapidApiKey, movieRepository);
            if (tempMovie != null)
                output.add(tempMovie);
        });

        return output;
    }


    public List<Movie> getFiveMovies(String title, int counter){
        List<Movie> output = new ArrayList<>();
        List<Title> movieResult = rapid.getTitles(title, rapidApiKey, rapid.MOVIE);

        if (counter >= 0 && counter <= movieResult.size() - 1)
        for (int i = counter; i < counter + 5; i++){
            if (i <= movieResult.size() - 1){
                Movie tempMovie = rapid.getMovieByImdbId(movieResult.get(i).getImdb_id(), rapidApiKey, movieRepository);
                if (tempMovie != null)
                    output.add(tempMovie);
            }
        }

        return output;
    }

    public Movie getById(String id) {
        return movieRepository.getByID(id);
    }
}
