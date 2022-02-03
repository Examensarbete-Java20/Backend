package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.MovieException;
import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Exception.UserException;
import com.example.examensarbete.Exception.WatchListException;
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
    private RegExp regExp = new RegExp();

    private final UserService userService;
    private final WatchlistService watchlistService;

    /**
     * Methods for watchlists
     */

    @PostMapping("/watchlist")
    public ResponseEntity<?> createWatchList(@RequestBody WatchList watchList){
        if (regExp.inputValidation(watchList.getTitle()))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.createList(watchList));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/watchlist/{googleId}")
    public ResponseEntity<?> getWatchListByGoogleId(@PathVariable String googleId){
        if (regExp.inputValidation(googleId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListByGoogleId(googleId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<?> getWatchListById(@PathVariable String id){
        if (regExp.inputValidation(id))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.getWatchListById(id));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/watchlist/movie/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Movie movie, @PathVariable String listId){
        if (regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addMovieToWatchList(movie,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/watchlist/series/{listId}")
    public ResponseEntity<?> addMovieToWatchList(@RequestBody Series series, @PathVariable String listId){
        if (regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.addSeriesToWatchList(series,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/watchlist/content/{listId}")
    public ResponseEntity<?> removeMovieFromWatchList(@RequestBody Content content, @PathVariable String listId){
        if (regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeContentFromWatchList(content,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/watchlist/list/{listId}/{username}")
    public ResponseEntity<?> inviteUserToList(@PathVariable String username, @PathVariable String listId){
        if (regExp.userValidation(username) && regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.inviteUserToWatchList(username,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PostMapping("/watchlist/list/removeUser/{listId}/{username}")
    public ResponseEntity<?> removeUserFromInviteInWatchList(@PathVariable String username, @PathVariable String listId){
        if (regExp.userValidation(username) && regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeUserFromInviteInWatchList(username,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @PutMapping("/watchlist/list/{listId}")
    public ResponseEntity<?> acceptInviteToList(@RequestBody User user, @PathVariable String listId){
        if (regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.acceptInviteToList(user,listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    @DeleteMapping("/watchlist/remove/{listId}")
    public ResponseEntity<?> removeList(@PathVariable String listId){
        if (regExp.inputValidation(listId))
        return ResponseEntity.status(HttpStatus.OK).body(watchlistService.removeList(listId));

        throw new WatchListException("NO SPECIAL CHARACTERS ALLOWED");
    }

    /**
     * Methods for user
     */

    @GetMapping("/{googleId}/{username}")
    public ResponseEntity<?> changeUsername(@PathVariable String googleId, @PathVariable String username) {
        if (regExp.userValidation(username) && regExp.inputValidation(googleId))
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeUsername(googleId, username));

        throw new UserException("NO SPECIAL CHARACTERS ALLOWED");
    }
}