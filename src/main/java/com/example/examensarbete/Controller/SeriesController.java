package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Serie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Service.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/serie")
public class SeriesController {
    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    @Autowired
    private SerieService serieService;





    @GetMapping("/title/{title}")
    public ResponseEntity<List<Title>> getID(@PathVariable String title){
        return ResponseEntity.ok(serieService.fetchTitle(title, rapidApiKey));
//        try{
//            return ResponseEntity.ok(fetchService.fetchTitle(title, rapidApiKey));
//        }
//        catch(Exception e){}
//        return null;
    }

    @GetMapping("/{imdb_id}")
    public ResponseEntity<Serie> getSerie(@PathVariable String imdb_id){
        return ResponseEntity.ok(serieService.fetchSerie(imdb_id, rapidApiKey));
    }
}
