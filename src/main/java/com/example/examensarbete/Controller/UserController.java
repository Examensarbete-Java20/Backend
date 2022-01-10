package com.example.examensarbete.Controller;

import com.example.examensarbete.Exception.SeriesException;
import com.example.examensarbete.Model.User;
import com.example.examensarbete.Service.SeriesService;
import com.example.examensarbete.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user){
            return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
    }

    @GetMapping("/{googleId}")
    public ResponseEntity<?> getUserByGoogleID (@PathVariable String googleId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(googleId));
    }

}
