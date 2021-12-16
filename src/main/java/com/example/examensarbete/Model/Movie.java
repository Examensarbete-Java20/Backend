package com.example.examensarbete.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    String imdb_id;
    String title;
    int year;
//    String description;
//    double movie_length;
//    double rating;
//    String trailer;
//    String image_url;
//    Date release;




}

