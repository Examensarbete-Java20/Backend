package com.example.examensarbete.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {
    @Id
    private String ID;
    private String title;
    @DocumentReference
    private User user;
    @DocumentReference
    private List<User> users;
    @DocumentReference
    private List<User> invited;
    @DocumentReference
    private List<Movie> movies;
    @DocumentReference
    private List<Series> series;

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void inviteUser(User user) {
        this.invited.add(user);
    }

    public void removeInvite(String username) {
        this.invited.remove(username);
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