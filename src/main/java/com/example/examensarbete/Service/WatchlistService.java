package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.UserException;
import com.example.examensarbete.Model.User;
import com.example.examensarbete.Model.WatchList;
import com.example.examensarbete.Repositories.UserRepository;
import com.example.examensarbete.Repositories.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private UserRepository userRepository;


    public WatchList createList(WatchList watchList) {
        User user = userRepository.getByGoogleId(watchList.getUser().getGoogleId());
        if (user == null)
            throw new UserException("User with that googleId doesn't exist");

        watchList.addUser(user);
        watchList.setUser(user);

        return watchListRepository.save(watchList);
    }


    public List<WatchList> getWatchListByGoogleId(String googleId){
        // TODO: Förbättra på något sätt
        List<WatchList> output = new ArrayList<>();
        List<WatchList> temp = watchListRepository.findAll();
        for (WatchList list: temp) {
            for (User u: list.getUsers()) {
                if (u.getGoogleId().equals(googleId)){
                    output.add(list);
                    break;
                }
            }
        }

       return output;
    }

}
