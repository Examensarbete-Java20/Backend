package com.example.examensarbete.Service;

import com.example.examensarbete.Model.User;
import com.example.examensarbete.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail()))
            return null;//TODO: kasta exeption eller något xDDD
        return userRepository.save(user);
    }

    public User loginUser(String googleId) {
        return userRepository.getByGoogleId(googleId);
    }
}
