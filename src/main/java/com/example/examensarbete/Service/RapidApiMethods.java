package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Repositories.SeriesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RapidApiMethods {
    public String MOVIE = "MOVIE";
    public String SERIES = "SERIES";

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getMovieEndpoint(boolean meta, String titleOrID) {
        if (meta)
            return "https://data-imdb1.p.rapidapi.com/movie/id/" + titleOrID + "/";
        return "https://data-imdb1.p.rapidapi.com/movie/imdb_id/byTitle/" + titleOrID + "/";
    }

    public String getSeriesEndpoint(boolean meta, String titleOrID) {
        if (meta)
            return "https://data-imdb1.p.rapidapi.com/series/id/" + titleOrID + "/";
        return "https://data-imdb1.p.rapidapi.com/series/idbyTitle/" + titleOrID + "/";
    }

    public HttpEntity<String> getEntity(String titleOrId, String rapidApiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.add("x-rapidapi-host", "data-imdb1.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(titleOrId, headers);
    }

    public Movie getMovieByImdbId(String imdbId, String rapidApiKey, MovieRepository repo) {
        Movie output = repo.getByImdbId(imdbId);

        if (output == null) {
            try {
                ResponseEntity<String> result = restTemplate.exchange(getMovieEndpoint(true, imdbId), HttpMethod.GET, getEntity(imdbId, rapidApiKey), String.class);
                if (result.getStatusCode().is2xxSuccessful() && result.getBody().length() != 14) {
                    output = objectMapper.readValue(result.getBody().substring(11, result.getBody().length() - 1), new TypeReference<>() {
                    });
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return output;
    }

    public Series getSeriesByImdbId(String imdbId, String rapidApiKey, SeriesRepository repo) {
        Series output = repo.getByImdbId(imdbId);

        if (output == null) {
            try {
                ResponseEntity<String> result = restTemplate.exchange(getSeriesEndpoint(true, imdbId), HttpMethod.GET, getEntity(imdbId, rapidApiKey), String.class);
                if (result.getStatusCode().is2xxSuccessful()) {
                    Series tempMovie = objectMapper.readValue(result.getBody().substring(11, result.getBody().length() - 1), new TypeReference<>() {
                    });
                    output = tempMovie;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }

        return output;
    }

    public List<Title> getTitles(String title, String rapidApiKey, String type) {
        List<Title> output = new ArrayList<>();
        String endPoint = "";
        if (type == "MOVIE")
            endPoint = getMovieEndpoint(false, title);
        else
            endPoint = getSeriesEndpoint(false, title);

        try {
            ResponseEntity<String> result = restTemplate.exchange(endPoint, HttpMethod.GET, getEntity(title, rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful())
                output = objectMapper.readValue(result.getBody().substring(11, result.getBody().length() - 1), new TypeReference<>() {
                });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }

}
