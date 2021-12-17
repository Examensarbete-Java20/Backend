package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Serie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Service.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/serie")
public class SeriesController {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    SerieService fetchService = new SerieService();




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
    public ResponseEntity<Serie> getSerie(@PathVariable String imdb_id){
        return ResponseEntity.ok(fetchService.fetchSerie(imdb_id, rapidApiKey));
    }
}