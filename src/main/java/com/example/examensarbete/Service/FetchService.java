package com.example.examensarbete.Service;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class FetchService {
    RestTemplate rt = new RestTemplate();
    ObjectMapper om = new ObjectMapper();


    public List<Title> fetchTitle(String title, String rapidApiKey){
        System.out.println(rapidApiKey);

        String jsonDto = title;
        List<Title> output = new ArrayList<>();


        String url = "https://data-imdb1.p.rapidapi.com/movie/imdb_id/byTitle/" + jsonDto + "/";
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.add("x-rapidapi-host", "data-imdb1.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonDto, headers);
        try {
            ResponseEntity<String> result = rt.exchange(url, method, entity, String.class);
            System.out.println(result);
            if (result.getStatusCode().is2xxSuccessful()) {
                List<Title> utput = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output.add(utput.get(0));
                output.add(utput.get(1));
                output.add(utput.get(2));
                output.add(utput.get(3));
                output.add(utput.get(4));

            }
        } catch (Exception e){
            System.out.println("CONNECTION FAIL");
        }

        return output;
    }

    public Movie fetchMovie(String imdb_id, String rapidApiKey){
        String jsonDto = imdb_id;
        Movie output = null;


        String url = "https://data-imdb1.p.rapidapi.com/movie/id/" + jsonDto + "/";
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.add("x-rapidapi-host", "data-imdb1.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonDto, headers);
        try {
            ResponseEntity<String> result = rt.exchange(url, method, entity, String.class);
            System.out.println(result);
            if (result.getStatusCode().is2xxSuccessful()) {
                Movie utput = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                System.out.println(utput);
                output = utput;
            }
        } catch (Exception e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }

        return output;

    }
}
