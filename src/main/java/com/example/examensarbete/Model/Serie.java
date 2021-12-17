package com.example.examensarbete.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {

    String ID;
    int year;
    double movie_length;
    double rating;
    String imdb_id;
    String title;
    String description;
    String plot;
    String trailer;
    String release;
    String image_url;
    String banner;
}
