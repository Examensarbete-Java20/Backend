package com.example.examensarbete.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String ID;
    private String googleId;
    private String username;
    private String email;
    private boolean isAdmin;

}
