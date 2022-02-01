package com.example.examensarbete.Service;

import com.example.examensarbete.Exception.UserException;
import com.example.examensarbete.Model.*;
import com.example.examensarbete.Repositories.MovieRepository;
import com.example.examensarbete.Repositories.SeriesRepository;
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
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;

    // TODO: Bryt ut ifsen som är likadana till en privat metod

    public WatchList createList(WatchList watchList) {
        User user = userRepository.getByGoogleId(watchList.getUser().getGoogleId()).get();
        if (user == null)
            throw new UserException("User with that googleId doesn't exist");

        watchList.addUser(user);
        watchList.setUser(user);

        return watchListRepository.save(watchList);
    }

    public WatchList getWatchListById(String id){
        return watchListRepository.getByID(id);
    }

    public List<WatchList> getWatchListByGoogleId(String googleId) {
        // TODO: Förbättra på något sätt
        List<WatchList> output = new ArrayList<>();
        List<WatchList> temp = watchListRepository.findAll();
        for (WatchList list : temp) {
            for (User u : list.getUsers()) {
                if (u.getGoogleId().equals(googleId)) {
                    output.add(list);
                    break;
                }
            }
        }

        return output;
    }

    public WatchList addMovieToWatchList(Movie movie, String listId) {
        WatchList list = watchListRepository.getByID(listId);

        Movie movieToSave = movieRepository.getByImdbId(movie.getImdbId());
        if (movieToSave == null)
            movieToSave = movieRepository.save(movie);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }

        if (list.contentExist(movieToSave)) {
            System.out.println("den finns");
            // TODO: bättre response
            return null;
        }
        list.addContent(movieToSave);

        return watchListRepository.save(list);
    }


    public WatchList addSeriesToWatchList(Series series, String listId) {
        WatchList list = watchListRepository.getByID(listId);
        Series seriesToSave = seriesRepository.getByImdbId(series.getImdbId());
        if (seriesToSave == null)
            seriesToSave = seriesRepository.save(series);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }

        if (list.contentExist(seriesToSave)) {
            System.out.println("den finns");
            // TODO: bättre response
            return null;
        }

        list.addContent(seriesToSave);

        return watchListRepository.save(list);
    }

    public WatchList removeContentFromWatchList(Content content, String listId) {
        WatchList list = watchListRepository.getByID(listId);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }

        if (!list.contentExist(content)) {
            System.out.println("den finns inte");
            // TODO: bättre response
            return null;
        }

        list.removeContent(content);

        return watchListRepository.save(list);
    }


    public WatchList inviteUserToWatchList(String username, String listId) {
        User user = userRepository.getByUsername(username);
        WatchList list = watchListRepository.getByID(listId);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }
        if (user == null) {
            System.out.println("user är null");
            // TODO: bättre response
            return null;
        }


        if (list.userExistInUsers(user)) {
            System.out.println("user Finns redan i userlist");
            // TODO: bättre response
            return null;
        } else if (list.userExistInInvite(user)) {
            System.out.println("user Finns redan i invited");
            // TODO: bättre response
            return null;
        }

        list.inviteUser(user);
        return watchListRepository.save(list);
    }

    public WatchList removeUserFromInviteInWatchList(String username, String listId) {
        User user = userRepository.getByUsername(username);
        WatchList list = watchListRepository.getByID(listId);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }
        if (user == null) {
            System.out.println("user är null");
            // TODO: bättre response
            return null;
        }


        if (!list.userExistInInvite(user)) {
            System.out.println("user Finns inte i invited");
            // TODO: bättre response
            return null;
        }

        list.removeInvite(user);
        return watchListRepository.save(list);
    }

    public WatchList acceptInviteToList(User user, String listId) {
        WatchList list = watchListRepository.getByID(listId);

        if (list == null) {
            System.out.println("listan är null");
            // TODO: bättre response
            return null;
        }
        if (user == null) {
            System.out.println("user är null");
            // TODO: bättre response
            return null;
        }

        if (list.userExistInUsers(user)) {
            System.out.println("user finns redan");
            // TODO: bättre response
            return null;
        } else if (!list.userExistInInvite(user)) {
            System.out.println("user finns inte");
            // TODO: bättre response
            return null;
        }
            list.removeInvite(user);
            list.addUser(user);

        return watchListRepository.save(list);
    }

    public WatchList removeList(String listId) {
        WatchList list = watchListRepository.getByID(listId);
        watchListRepository.delete(list);
        return list;
    }
}
