package com.example.examensarbete.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {
    @Id
    private String ID;
    private String title;
    private User user;
    private List<User> users;
    private List<Movie> movies;
    private List<Series> series;

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }

    public void addSeries(Series series) {
        this.series.add(series);
    }

    public void removeSeries(Series series) {
        this.series.remove(series);
    }
}
