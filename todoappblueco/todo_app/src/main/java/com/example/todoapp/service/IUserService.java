package com.example.todoapp.service;


import com.example.todoapp.entity.MainResponse;
import com.example.todoapp.entity.User;
import com.example.todoapp.entity.UserLogin;
import com.example.todoapp.entity.request.AuthentificationOtp;
import com.example.todoapp.entity.request.ResetPassword;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService  {

    User findUserByEmail(String email);
    MainResponse createUser(User user);
    void activeUser(String token);
    MainResponse login(UserLogin user);
    MainResponse resetPasswordViaEmail(String email);
    void sendResetPasswordViaEmail(String email);

    MainResponse authentificationotp(AuthentificationOtp authentificationOtp);

    MainResponse resetPassword(ResetPassword resetPassword);


}
