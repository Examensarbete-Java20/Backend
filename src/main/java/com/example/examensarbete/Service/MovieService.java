package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Exception.MovieException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    private RapidApiMethods rapid = new RapidApiMethods();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MovieRepository movieRepository;


    public List<Title> fetchTitle(String title){
        List<Title> output = new ArrayList<>();
            try {
            ResponseEntity<String> result = restTemplate.exchange(rapid.getMovieEndpoint(false,title), HttpMethod.GET, rapid.getEntity(title,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()  && result.getBody().length() != 14) {
                List<Title> tempList = objectMapper.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempList.stream().limit(5).collect(Collectors.toList());
                System.out.println(output);

            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        if (output.size() == 0)
            throw new MovieException(HttpStatus.NOT_FOUND + "  Cant find anything on that title");

        return output;
    }

    public Movie fetchMovie(String imdbId){
        Movie output = movieRepository.getByImdbId(imdbId);

        if (output == null){
            try {
                ResponseEntity<String> result = restTemplate.exchange(rapid.getMovieEndpoint(true, imdbId), HttpMethod.GET, rapid.getEntity(imdbId, rapidApiKey), String.class);
                if (result.getStatusCode().is2xxSuccessful() && result.getBody().length() != 14) {
                    output = objectMapper.readValue(result.getBody().substring(11, result.getBody().length() - 1), new TypeReference<>() {
                    });
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (output == null)
            throw new MovieException(HttpStatus.NOT_FOUND + "  Movie not found");

        return output;
    }

    public Movie getMovieByImdbId(String imdbId){
        Movie temp = movieRepository.getByImdbId(imdbId);

        if (temp == null)
            throw new MovieException(HttpStatus.NOT_FOUND + " Movie not found");

        return temp;
    }

    public Movie saveMovie(Movie movie){
        Movie temp = movieRepository.getByImdbId(movie.getImdbId());

        if (temp != null)
            throw new MovieException(HttpStatus.BAD_REQUEST + " Movie already exists in database");

        movie.setExist(true);
       return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie){
         Movie temp = movieRepository.getByID(movie.getID());

        if (temp == null)
            throw new MovieException(HttpStatus.BAD_REQUEST + " Movie not found");

            movie.setOwnRating(movie.getTotalRating() / movie.getTotalOfVoters());
            return movieRepository.save(movie);
    }
    public List<Movie> getTopTen(){
         List<Movie> temp = movieRepository.findAll();
        temp.sort((movie1, movie2) -> Double.compare(movie2.getOwnRating(), movie1.getOwnRating()));

         return temp.stream().limit(10).collect(Collectors.toList());
    }
}
