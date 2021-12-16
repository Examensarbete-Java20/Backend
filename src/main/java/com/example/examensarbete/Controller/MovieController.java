package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Title;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/movie")
public class MovieController {
    RestTemplate rt = new RestTemplate();
    ObjectMapper om = new ObjectMapper();


    @GetMapping("/{title}")
    public List<Title> getID(@PathVariable String title){


        String jsonDto = title;
        List<Title> output = new ArrayList<>();


        String url = "https://data-imdb1.p.rapidapi.com/movie/imdb_id/byTitle/" + jsonDto + "/";
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", "hej");
        headers.add("x-rapidapi-host", "data-imdb1.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonDto, headers);
        try {
            ResponseEntity<String> result = rt.exchange(url, method, entity, String.class);
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
}
