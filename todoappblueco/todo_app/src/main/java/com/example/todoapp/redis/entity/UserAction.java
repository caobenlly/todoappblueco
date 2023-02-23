package com.example.todoapp.redis.entity;

import com.example.todoapp.utils.ShareConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;


@RedisHash(ShareConstants.RedisHashKey.USER_ACTION)
@Data
public class UserAction {
    @Id
    private String token;// phone+action


    private String email;
}
