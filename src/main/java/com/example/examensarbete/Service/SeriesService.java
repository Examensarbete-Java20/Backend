package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.SeriesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public Series updateSeriesRating(Series series, String googleId, int rating) {
        int oldRating = 0;
        int addVote = 1;
        String vote = googleId + ", " + rating;
        if (series.getVoters() == null){
            List<String> list = new ArrayList<>();
            list.add(vote);
            series.setVoters(list);
        } else {
            for (String s: series.getVoters()) {
                if (s.contains(googleId)) {
                    oldRating = Integer.parseInt(s.substring(s.indexOf(" ")+1));
                    addVote = 0;
                    series.getVoters().remove(s);
                    series.getVoters().add(vote);
                    break;
                }
            }
        }


        series.setTotalRating(series.getTotalRating() + rating - oldRating);
        series.setTotalOfVoters(series.getTotalOfVoters() + addVote);
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

    public List<Series> fetchTitle(String title){
        List<Series> output = new ArrayList<>();
        List<Title> seriesResult = rapid.getTitles(title, rapidApiKey, rapid.SERIES);

        seriesResult.forEach(serie -> {
            Series tempSeries = rapid.getSeriesByImdbId(serie.getImdb_id(), rapidApiKey, seriesRepository);
            if (tempSeries != null)
                output.add(tempSeries);
        });

        return output;
    }


    public List<Series> getFiveSeries(String title, int counter){
        List<Series> output = new ArrayList<>();
        List<Title> seriesResult = rapid.getTitles(title, rapidApiKey, rapid.SERIES);

        if (counter >= 0 && counter <= seriesResult.size() - 1)
        for (int i = counter; i < counter + 5; i++){
            if (i <= seriesResult.size() - 1) {
                Series tempSeries = rapid.getSeriesByImdbId(seriesResult.get(i).getImdb_id(), rapidApiKey, seriesRepository);
                if (tempSeries != null)
                    output.add(tempSeries);
            }
        }

        return output;
    }


    public Series getById(String id) {
        return seriesRepository.getByID(id);
    }

}

