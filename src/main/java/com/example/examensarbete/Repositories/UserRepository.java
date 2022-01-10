package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User getByGoogleId(String googleId);
}
