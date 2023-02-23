package com.example.todoapp.service;


import com.example.todoapp.controller.UserController;
import com.example.todoapp.entity.*;
import com.example.todoapp.entity.request.AuthentificationOtp;
import com.example.todoapp.entity.request.ResetPassword;
import com.example.todoapp.event.OnResetPasswordViaEmailEvent;
import com.example.todoapp.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.example.todoapp.exceptions.AppException;
import com.example.todoapp.exceptions.ErrorResponseBase;
import com.example.todoapp.redis.entity.UserAction;
import com.example.todoapp.redis.reponsitory.UserActionRepository;
import com.example.todoapp.reponsitory.RegistrationUserTokenRepository;
import com.example.todoapp.reponsitory.ResetPasswordTokenRepository;
import com.example.todoapp.reponsitory.UserRepository;
import org.jboss.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private RegistrationUserTokenRepository registrationUserTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private  UserActionRepository actionRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    private  static Logger logger = Logger.getLogger(UserController.class);

    @Override
    public MainResponse createUser(User user) {

        logger.info("Tạo user mới");
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // create user
        userRepository.save(user);

        // create new user registration token
        final String newToken = UUID.randomUUID().toString();
        RegistrationUserToken token = new RegistrationUserToken(newToken, user);



        UserAction userAction = new UserAction();
        userAction.setToken(newToken);
        userAction.setEmail(user.getEmail());

        //add cache
        actionRepository.save(userAction);
        registrationUserTokenRepository.save(token);

        // send email to confirm
        sendConfirmUserRegistrationViaEmail(user.getEmail());

        MainResponse response = new MainResponse();
        response.setToken(newToken);
        response.setMessage("Đăng ký thành công");


        logger.info("User đã được thêm");
        return response;
    }

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email).get();
    }

//    private void createNewRegistrationUserToken(User user) {
//
//        // create new token for confirm Registration
//        final String newToken = UUID.randomUUID().toString();
//        RegistrationUserToken token = new RegistrationUserToken(newToken, user);
//
//
//
//        UserAction userAction = new UserAction();
//        userAction.setToken(newToken);
//        userAction.setEmail(user.getEmail());
//
//        //add cache
//        actionRepository.save(userAction);
//        registrationUserTokenRepository.save(token);
//
//
//    }

    public void sendConfirmUserRegistrationViaEmail(String email) {
        eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
    }

    @Override
    public void activeUser(String token) {
        RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

        User user = registrationUserToken.getUser();
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        // remove Registration User Token
        registrationUserTokenRepository.deleteById(registrationUserToken.getId());
    }


    public MainResponse login(UserLogin user){
        logger.info("Đăng nhập user");
        Optional<User> checkEx = userRepository.findByEmail(user.getEmail());

        if (checkEx == null){
            throw new AppException(ErrorResponseBase.USER_NOT_EXISTED);
        }

        if (checkEx.get().getStatus().equals(UserStatus.NOT_ACTIVE)  ){
            throw  new AppException(ErrorResponseBase.NOT_ACTIVE);
        }

        if (!passwordEncoder.matches(user.getPassword(), checkEx.get().getPassword())){
            throw new AppException(ErrorResponseBase.PASSWORD_INVALID );
        }

        String token = UUID.randomUUID().toString();

        MainResponse response = new MainResponse();
        response.setToken(token);
        response.setMessage("Đăng nhập thành công" );

        return response;

    }


    @Override
    public MainResponse resetPasswordViaEmail(String email) {
        logger.info("Quên mật khẩu");
        // find user by email
        User user = findUserByEmail(email);
        if(user == null){
            throw new AppException(ErrorResponseBase.USER_NOT_EXISTED);
        }


       String resetPasswordToken = resetPasswordTokenRepository.findByUserId(user.getId());
        if( resetPasswordToken!= null){
            // remove Reset Password
           ResetPasswordToken resetPasswordToken1 = resetPasswordTokenRepository.findByToken(resetPasswordToken);
            int otp = (int) Math.floor(((Math.random() * 899999) + 100000));
           resetPasswordToken1.setOtp(otp);
        }else {
            // create new reset password OTP
            createNewResetPasswordOTP(user);
        }
        // send email
        sendResetPasswordViaEmail(email);


       //get token

        String otp = resetPasswordTokenRepository.findByUserId(user.getId());
        MainResponse response = new MainResponse();
        response.setToken(otp);
        response.setMessage("OTP đã được gửi vui lòng check email");

        return response;
    }

    @Override
    public void sendResetPasswordViaEmail(String email) {
        eventPublisher.publishEvent(new OnResetPasswordViaEmailEvent(email));
    }

    @Override
    public MainResponse authentificationotp(AuthentificationOtp authentificationOtp) {
        logger.info("xác thực otp");


        int otpauthen = resetPasswordTokenRepository.findByOtp(findUserByEmail(authentificationOtp.getEmail()).getId());


        String s = String.valueOf(otpauthen);
        if (authentificationOtp.getOtp().equals(s) == false ){
            throw new AppException(ErrorResponseBase.NOT_OTP);
        }

        if(findUserByEmail(authentificationOtp.getEmail())==null){
            throw  new AppException(ErrorResponseBase.NOT_EXISTED);
        }
        User user = findUserByEmail(authentificationOtp.getEmail());

        String token = resetPasswordTokenRepository.findByUserId(user.getId());
        MainResponse response = new MainResponse();
        response.setToken(token);
        response.setMessage("Xác thực thành công");
        return response;
    }

    @Override
    public MainResponse resetPassword(ResetPassword resetPassword) {

        logger.info("đổi mật khẩu");
// get token
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(resetPassword.getToken());

        if(findUserByEmail(resetPassword.getEmail()) == null){
            throw new AppException(ErrorResponseBase.NOT_FOUND);
        }
        // change password
        User user = resetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPassword.getNewpassword()));
        userRepository.save(user);

        // remove Reset Password
        resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());


        MainResponse response = new MainResponse();
        response.setToken(resetPassword.getToken());
        response.setMessage("Đổi mật khẩu thành công");
        return response;
    }

    private void createNewResetPasswordOTP(User user) {


        // create new token for Reseting password
        final int newToken = (int) Math.floor(((Math.random() * 899999) + 100000));
        final String newTokenUser = UUID.randomUUID().toString();
        ResetPasswordToken token = new ResetPasswordToken(newToken, user,newTokenUser);

        resetPasswordTokenRepository.save(token);

    }
}
