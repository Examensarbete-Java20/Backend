package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> getByGoogleId(String googleId);
    //User getByGoogleId(String googleId);
    User getByUsername(String username);

}
