package com.korit.backend_mini.repository;

import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    public Optional<User> getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public Optional<User> addUser(User user) {
        try {
            userMapper.addUser(user);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
