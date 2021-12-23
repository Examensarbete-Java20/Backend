package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<Series, String> {
    Series getByImdbId(String imdbId);
    Series getByID(String ID);
}
