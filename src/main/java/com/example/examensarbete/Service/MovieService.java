package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private RapidApiMethods rapid = new RapidApiMethods();
    private RestTemplate rt = new RestTemplate();
    private ObjectMapper om = new ObjectMapper();


    public List<Title> fetchTitle(String title, String rapidApiKey){
        List<Title> output = new ArrayList<>();

        try {
            ResponseEntity<String> result = rt.exchange(rapid.getMovieEndpoint(false,title), HttpMethod.GET, rapid.getEntity(title,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                List<Title> tempList = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempList.stream().limit(5).collect(Collectors.toList());

            }
        } catch (Exception e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }

        return output;
    }

    public Movie fetchMovie(String imdb_id, String rapidApiKey){
        Movie output = null;

        try {
            ResponseEntity<String> result = rt.exchange(rapid.getMovieEndpoint(true,imdb_id), HttpMethod.GET, rapid.getEntity(imdb_id,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                Movie tempMovie = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempMovie;
            }
        } catch (Exception e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }

        return output;

    }
}
