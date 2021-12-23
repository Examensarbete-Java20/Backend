package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.MovieException;
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
    private RestTemplate rt = new RestTemplate();
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private SeriesRepository seriesRepository;


    public List<Title> fetchTitle(String title){
        List<Title> output = new ArrayList<>();
        try {
            ResponseEntity<String> result = rt.exchange(rapid.getSeriesEndpoint(false,title), HttpMethod.GET, rapid.getEntity(title,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                System.out.println(result.getBody().substring(11, result.getBody().length()-1));
                List<Title> tempList = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempList.stream().limit(5).collect(Collectors.toList());

            }
        } catch (JsonProcessingException e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }
        if (output.size() == 0)
            throw new SeriesException(HttpStatus.NOT_FOUND + "  Cant find anything on that title");

        return output;
    }

    public Series getSeriesByImdbId(String imdb_id){
        Series output = seriesRepository.getByImdbId(imdb_id);

        try {
            ResponseEntity<String> result = rt.exchange(rapid.getSeriesEndpoint(true,imdb_id), HttpMethod.GET, rapid.getEntity(imdb_id,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                Series tempMovie = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempMovie;
            }
        } catch (JsonProcessingException e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }
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

    public Series updateSeries(Series series){
        Series temp = seriesRepository.getByID(series.getID());

        if (temp == null)
            throw new SeriesException(HttpStatus.BAD_REQUEST + " Series not found");

        series.setOwnRating(series.getTotalRating() / series.getTotalOfVoters());
        return seriesRepository.save(series);
    }
    public List<Series> getTopTen(){
        List<Series> temp = seriesRepository.findAll();
        temp.sort((movie1, movie2) -> Double.compare(movie2.getOwnRating(), movie1.getOwnRating()));

        return temp.stream().limit(10).collect(Collectors.toList());
    }

}
