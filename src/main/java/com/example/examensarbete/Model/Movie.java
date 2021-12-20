package com.example.examensarbete.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;




@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @Id
    String ID;

    int year;
    double movie_length;
    double rating;

    @JsonProperty("imdb_id")
    String imdbId;
    String title;
    String description;
    String plot;
    String trailer;
    String release;
    String image_url;
    String banner;
}

