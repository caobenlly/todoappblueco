package com.example.todoapp.controller;



import com.example.todoapp.entity.RegistrationUserToken;
import com.example.todoapp.entity.ResetPasswordToken;
import com.example.todoapp.entity.User;
import com.example.todoapp.entity.UserLogin;
import com.example.todoapp.entity.request.AuthentificationOtp;
import com.example.todoapp.entity.request.ResetPassword;
import com.example.todoapp.reponsitory.RegistrationUserTokenRepository;
import com.example.todoapp.reponsitory.ResetPasswordTokenRepository;
import com.example.todoapp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private IUserService userService;
    @Autowired
    private RegistrationUserTokenRepository registrationUserToken;
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {

        // create User
//        userService.createUser(user);
//        RegistrationUserToken tokenEntity= registrationUserToken.findFirstByUserId(user.getId());
//        String token = tokenEntity.getToken();
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @GetMapping("/activeUser")
    // validate: check exists, check not expired
    public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

        // active user
        userService.activeUser(token);

        return new ResponseEntity<>("Active success!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin user) {

        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }


    // reset password confirm
    @GetMapping("/resetPasswordRequest")
    // validate: email exists, email not active
    public ResponseEntity<?> sendResetPasswordViaEmail(@RequestParam String email) {

//        userService.resetPasswordViaEmail(email);


        return new ResponseEntity<>(userService.resetPasswordViaEmail(email), HttpStatus.OK);
    }

    @GetMapping("/authentificationotp")
    // validate: email exists, email not active
    public ResponseEntity<?> authentificationotp(@Valid @RequestBody AuthentificationOtp authentificationOtp) {

        userService.authentificationotp(authentificationOtp);

        return new ResponseEntity<>(userService.authentificationotp(authentificationOtp), HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    // validate: check exists, check not expired
    public ResponseEntity<?> resetPasswordViaEmail(@Valid @RequestBody ResetPassword resetPassword) {

        // reset password
        userService.resetPassword(resetPassword);

        return new ResponseEntity<>("Reset Password success!", HttpStatus.OK);
    }
}
