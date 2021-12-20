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

    public Movie fetchMovie(String imdb_id){
        Movie output = null;

        try {
            ResponseEntity<String> result = restTemplate.exchange(rapid.getMovieEndpoint(true,imdb_id), HttpMethod.GET, rapid.getEntity(imdb_id,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()  && result.getBody().length() != 14) {
                output  = objectMapper.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        if (output == null)
            throw new MovieException(HttpStatus.NOT_FOUND + "  Movie not found");

        return output;
    }

    public Movie getmovieFromdb(String imdb_id){
        return movieRepository.findByImdb_id(imdb_id);
    }
}
