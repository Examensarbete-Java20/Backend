package com.example.examensarbete.Service;

import com.example.examensarbete.Model.CustomException;
import com.example.examensarbete.Model.Movie;
import com.example.examensarbete.Model.Title;
import com.example.examensarbete.Repositories.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private RapidApiMethods rapid = new RapidApiMethods();
    private RestTemplate rt = new RestTemplate();
    private ObjectMapper om = new ObjectMapper();

    private final MovieRepository movieRepository;

    @Value("${RAPID_API_KEY}")
    private String rapidApiKey;

    public List<Title> fetchTitle(String title){
        List<Title> output = new ArrayList<>();

        try {
            ResponseEntity<String> result = rt.exchange(rapid.getMovieEndpoint(false,title), HttpMethod.GET, rapid.getEntity(title,rapidApiKey), String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                List<Title> tempList = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});
                output = tempList.stream().limit(5).collect(Collectors.toList());


            }
        } catch (Exception e){
            System.out.println("CONNECTION FAIL");
            e.printStackTrace();
        }

        return output;
    }

    public ResponseEntity<?> fetchMovie(String imdb_id) {
        Movie output = null;

            ResponseEntity<String> result = rt.exchange(rapid.getMovieEndpoint(true,imdb_id), HttpMethod.GET, rapid.getEntity(imdb_id,rapidApiKey), String.class);

        try {
            if (result.getStatusCode().is2xxSuccessful() && result.getBody().length() != 14) {
                output = om.readValue(result.getBody().substring(11, result.getBody().length()-1), new TypeReference<>() {});

                movieRepository.save(output);

                log.info(String.valueOf(output));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (output == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomException.builder()
                                                                .message("Not a valid ID")
                                                                .status(HttpStatus.BAD_REQUEST)
                                                                .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
