package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.SeriesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    private RapidApiMethods rapid = new RapidApiMethods();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SeriesRepository seriesRepository;


    public List<Title> getTitles(String title){
        List<Title> output = rapid.getTitles(title, rapidApiKey, rapid.SERIES).stream().limit(5).collect(Collectors.toList());

        if (output.size() == 0)
            throw new SeriesException(HttpStatus.NOT_FOUND + "  Cant find anything on that title");

        return output;
    }

    public Series getSeriesByImdbId(String imdbId){
        Series output = rapid.getSeriesByImdbId(imdbId,rapidApiKey,seriesRepository);

        if (output == null)
            throw new SeriesException(HttpStatus.NOT_FOUND + "  Series not found");

        return output;
    }

    public Series saveSeries(Series series){
        Series temp = seriesRepository.getByImdbId(series.getImdbId());

        if (temp != null)
            throw new SeriesException(HttpStatus.BAD_REQUEST + " Series already exists in database");

        series.setExist(true);
        return seriesRepository.save(series);
    }

    public Series updateSeriesRating(Series series, int rating) {
        series.setTotalRating(series.getTotalRating() + rating);
        series.setTotalOfVoters(series.getTotalOfVoters() + 1);
        if (!series.isExist())
            series.setExist(true);

        series.setOwnRating(series.getTotalRating() / series.getTotalOfVoters());
        System.out.println(series);
        return seriesRepository.save(series);
    }

    public List<Series> getTopTen(){
        List<Series> temp = seriesRepository.findAll();
        temp.sort((movie1, movie2) -> Double.compare(movie2.getOwnRating(), movie1.getOwnRating()));

        return temp.stream().limit(10).collect(Collectors.toList());
    }

}
