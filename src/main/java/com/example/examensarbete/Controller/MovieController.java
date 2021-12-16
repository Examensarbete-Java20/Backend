package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Service.FetchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    FetchService fetchService = new FetchService();




    @GetMapping("/title/{title}")
    public ResponseEntity<List<Title>> getID(@PathVariable String title){
        return ResponseEntity.ok(fetchService.fetchTitle(title, rapidApiKey));
//        try{
//            return ResponseEntity.ok(fetchService.fetchTitle(title, rapidApiKey));
//        }
//        catch(Exception e){}
//        return null;
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String imdb_id){
        return ResponseEntity.ok(fetchService.fetchMovie(imdb_id, rapidApiKey));
    }
}
