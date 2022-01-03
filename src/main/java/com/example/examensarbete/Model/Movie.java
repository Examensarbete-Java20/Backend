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
    private String ID;

    @JsonProperty("imdb_id")
    private String imdbId;

    private double rating;

    private double ownRating;
    private int totalOfVoters;
    private int totalRating;

    private int year;
    private double movie_length;

    private String title;
    private String description;
    private String plot;
    private String trailer;
    private String release;
    private String image_url;
    private String banner;
    // exist för frontend ifall filmen finns i db eller inte sätts när filmen sparas
    private boolean exist;
}

