package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie getByImdbId(String imdbId);
    Movie getByID(String ID);
}
