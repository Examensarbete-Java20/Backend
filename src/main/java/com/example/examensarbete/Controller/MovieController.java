package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/movie")
public class MovieController {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;
    @Autowired
    private MovieService fetchService;

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


    @GetMapping("/getmovie/{imdb_id}")
    public ResponseEntity<Movie> getmoviefromdb(@PathVariable String imdb_id){
        return ResponseEntity.ok(fetchService.getmovieFromdb(imdb_id));
    }
}
