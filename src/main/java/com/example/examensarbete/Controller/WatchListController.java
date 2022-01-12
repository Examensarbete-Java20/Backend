package com.example.examensarbete.Controller;

import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Series;
import com.example.examensarbete.Model.User;
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

    // TODO : Fixa bättre returns xD

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody WatchList watchList){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.createList(watchList));
    }

    @GetMapping("/{googleId}")
    public ResponseEntity<?> getWatchListByGoogleId(@PathVariable String googleId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListByGoogleId(googleId));
    }

    @PostMapping("/movie/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Movie movie, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addMovieToWatchList(movie,listId));
    }

    @DeleteMapping("/movie/{listId}")
    public ResponseEntity<?> removeMovieFromWatchList(@RequestBody Movie movie, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeMovieFromWatchList(movie,listId));
    }

    @PostMapping("/series/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Series series, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addSeriesToWatchList(series,listId));
    }

    @DeleteMapping("/series/{listId}")
    public ResponseEntity<?> removeMovieFromWatchList(@RequestBody Series series, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeSeriesFromWatchList(series,listId));
    }

    @PostMapping("/list/{listId}/{username}")
    public ResponseEntity<?> inviteUserToList(@PathVariable String username, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.inviteUserToWatchList(username,listId));
    }

    @DeleteMapping("/list/{listId}/{username}")
    public ResponseEntity<?> removeUserFromInviteInWatchList(@PathVariable String username, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeUserFromInviteInWatchList(username,listId));
    }

    @PutMapping("/list/{listId}")
    public ResponseEntity<?> acceptInviteToList(@RequestBody User user, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.acceptInviteToList(user,listId));
    }
}
