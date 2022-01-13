package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.WatchList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatchListRepository extends MongoRepository<WatchList, String> {
    WatchList getByID(String id);
}
