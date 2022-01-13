package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Repositories.SeriesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieAndSeriesService {
    @Value("${RAPID_API_KEY}")
    private static String rapidApiKey;

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

        List<Title> movieResult = rapid.getTitles(title, rapidApiKey, rapid.MOVIE);
        List<Title> seriesResult = rapid.getTitles(title, rapidApiKey, rapid.SERIES);


        movieResult.forEach(movie -> {
            Movie tempMovie = rapid.getMovieByImdbId(movie.getImdb_id(), rapidApiKey, movieRepository);
            if (tempMovie != null)
                output.get(0).add(tempMovie);
        });

        seriesResult.forEach(series -> {
            Series tempSeries = rapid.getSeriesByImdbId(series.getImdb_id(), rapidApiKey, seriesRepository);
            if (tempSeries != null)
                output.get(1).add(tempSeries);
        });

        return output;
    }

    public static void main(String[] args) {
        System.out.println(rapidApiKey);
    }
}
