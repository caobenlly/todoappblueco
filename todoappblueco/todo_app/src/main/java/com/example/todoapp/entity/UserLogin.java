package com.example.todoapp.entity;

import lombok.Data;

@Data
public class UserLogin {

    String email;

    String password;



    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
