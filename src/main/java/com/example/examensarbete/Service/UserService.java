package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.UserException;
import com.example.examensarbete.Model.User;
import com.example.examensarbete.Repositories.RoleRepository;
import com.example.examensarbete.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User createUser(User user) {

        user.addDefaultRole(roleRepository);
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email taken");

        return userRepository.save(user);
    }

    public User loginUser(String googleId) {
        return userRepository.getByGoogleId(googleId).get();
    }

    public User changeUsername(String googleId, String newUsername) {
        User user = userRepository.getByGoogleId(googleId).get();
        if (user == null)
            throw new UserException("User with that googleId doesn't exist");
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

}
