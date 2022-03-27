package com.example.shoppingapi.service;

import com.example.shoppingapi.entity.User;

public interface UserService {
    Boolean login(String username, String password);
    User register(User newUser) throws Exception;
}
