package com.example.todoapp.redis.reponsitory;

import com.example.todoapp.redis.entity.UserAction;
import org.springframework.data.repository.CrudRepository;


public interface UserActionRepository extends CrudRepository<UserAction, String> {
}
