package com.example.todoapp.entity;

import lombok.Data;

@Data
public class MainResponse<T> {

   private String token;
   private String message;
   private T data;
}
