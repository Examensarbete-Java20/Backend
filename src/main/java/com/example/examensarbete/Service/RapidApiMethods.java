package com.example.examensarbete.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RapidApiMethods {

    public String getMovieEndpoint (boolean meta, String titleOrID) {
        if (meta)
            return "https://data-imdb1.p.rapidapi.com/movie/id/" + titleOrID + "/";
        return "https://data-imdb1.p.rapidapi.com/movie/imdb_id/byTitle/" + titleOrID + "/";
    }

    public String getSerieEndpoint (boolean meta, String titleOrID) {
        if (meta)
            return "https://data-imdb1.p.rapidapi.com/series/id/" + titleOrID + "/";
        return "https://data-imdb1.p.rapidapi.com/series/idbyTitle/" + titleOrID + "/";
    }

    public HttpEntity<String> getEntity(String titleOrId,String rapidApiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.add("x-rapidapi-host", "data-imdb1.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(titleOrId, headers);
    }
}
