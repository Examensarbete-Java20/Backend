package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Repositories.SeriesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieAndSeriesService {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;

    private RapidApiMethods rapid = new RapidApiMethods();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();


    public List<List> fetchTitle(String title) {
        List<List> output = new ArrayList<>();
        output.add(new ArrayList<Movie>());
        output.add(new ArrayList<Series>());
        try {
            ResponseEntity<String> moviesResult = restTemplate.exchange(rapid.getMovieEndpoint(false, title), HttpMethod.GET, rapid.getEntity(title, rapidApiKey), String.class);
            ResponseEntity<String> seriesResult = restTemplate.exchange(rapid.getSeriesEndpoint(false, title), HttpMethod.GET, rapid.getEntity(title, rapidApiKey), String.class);
            if (moviesResult.getStatusCode().is2xxSuccessful()) {
                List<Title> temp = objectMapper.readValue(moviesResult.getBody().substring(11, moviesResult.getBody().length() - 1), new TypeReference<>() {
                });
                temp.forEach(movie -> {
                    Movie tempMovie = rapid.getMovieByImdbId(movie.getImdb_id(), rapidApiKey, movieRepository);
                    if (tempMovie != null)
                        output.get(0).add(tempMovie);
                });
            }
            if (seriesResult.getStatusCode().is2xxSuccessful()) {
                List<Title> temp = objectMapper.readValue(seriesResult.getBody().substring(11, seriesResult.getBody().length() - 1), new TypeReference<>() {
                });
                temp.forEach(series -> {
                    Series tempSeries = rapid.getSeriesByImdbId(series.getImdb_id(), rapidApiKey, seriesRepository);
                    if (tempSeries != null)
                        output.get(1).add(tempSeries);
                });
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }
}
