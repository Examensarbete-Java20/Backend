package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.UserException;
import com.example.examensarbete.Model.*;
import com.example.examensarbete.Service.UserService;
import com.example.examensarbete.Service.WatchlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WatchlistService watchlistService;

    /**
     * Methods for watchlists
     */

    @PostMapping("/watchlist")
    public ResponseEntity<?> createWatchList(@RequestBody WatchList watchList){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.createList(watchList));
    }

    @GetMapping("/watchlist/{googleId}")
    public ResponseEntity<?> getWatchListByGoogleId(@PathVariable String googleId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListByGoogleId(googleId));
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<?> getWatchListById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListById(id));
    }

    @PostMapping("/watchlist/movie/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Movie movie, @PathVariable String listId){
        System.out.println("hej");
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addMovieToWatchList(movie,listId));
    }

    @PostMapping("/watchlist/series/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Series series, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addSeriesToWatchList(series,listId));
    }

    @PostMapping("/watchlist/content/{listId}")
    public ResponseEntity<?> removeMovieFromWatchList(@RequestBody Content content, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeContentFromWatchList(content,listId));
    }

    @PostMapping("/watchlist/list/{listId}/{username}")
    public ResponseEntity<?> inviteUserToList(@PathVariable String username, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.inviteUserToWatchList(username,listId));
    }

    @PostMapping("/watchlist/list/removeUser/{listId}/{username}")
    public ResponseEntity<?> removeUserFromInviteInWatchList(@PathVariable String username, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeUserFromInviteInWatchList(username,listId));
    }

    @PutMapping("/watchlist/list/{listId}")
    public ResponseEntity<?> acceptInviteToList(@RequestBody User user, @PathVariable String listId){
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.acceptInviteToList(user,listId));
    }

    /**
     * Methods for user
     */

    @GetMapping("/{googleId}/{username}")
    public ResponseEntity<?> changeUsername(@PathVariable String googleId, @PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.changeUsername(googleId, username));
        } catch (UserException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}