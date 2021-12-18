package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/movie")
public class MovieController {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    private final MovieService fetchService;

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Title>> getID(@PathVariable String title){
        return ResponseEntity.ok(fetchService.fetchTitle(title, rapidApiKey));
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<?> getMovie(@PathVariable String imdb_id){
        return fetchService.fetchMovie(imdb_id, rapidApiKey);
    }
}
