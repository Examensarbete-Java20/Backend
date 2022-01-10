package com.example.examensarbete.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String ID;
    private String googleId;
    private String username;
    private String email;
    private List<WatchList> watchLists;
    private boolean isAdmin;

    public void addWatchList(WatchList watchList) {
        this.watchLists.add(watchList);
    }

    public void removeWatchList(WatchList watchList) {
        this.watchLists.remove(watchList);
    }
}
