package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.Title;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends MongoRepository<Title, String> {
    Title findByTitle(String title);
}
