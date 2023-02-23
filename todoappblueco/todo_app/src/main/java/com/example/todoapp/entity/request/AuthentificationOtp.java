package com.example.todoapp.entity.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class AuthentificationOtp {
    @NotBlank
    private String email;
    
    @NotBlank
    private String otp;
}
