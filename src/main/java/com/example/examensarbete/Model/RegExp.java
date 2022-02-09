package com.example.examensarbete.Model;

import java.util.regex.Pattern;

public class RegExp {
    String userRegExp = "^\\d*[a-zA-Z][a-zA-Z\\d]*$";
    String watchListAndSearchBarRegExp = "^\\d*[a-zA-Z0-9 ][a-zA-Z0-9 \\d]*$";

    public boolean userValidation(String username){
        return Pattern.matches(userRegExp, username);
    }


    public boolean inputValidation(String input){
        return Pattern.matches(watchListAndSearchBarRegExp, input);
    }
}
