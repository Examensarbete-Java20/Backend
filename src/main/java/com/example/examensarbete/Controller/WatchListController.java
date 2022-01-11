package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.WatchList;
import com.example.examensarbete.Service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/watchlist")
public class WatchListController {
    @Autowired
    private WatchlistService watchlistService;


    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody WatchList watchList){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.createList(watchList));
    }

    @GetMapping("/{googleId}")
    public ResponseEntity<?> getWatchListByGoogleId(@PathVariable String googleId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListByGoogleId(googleId));
    }
}
