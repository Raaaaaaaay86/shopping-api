package com.example.shoppingapi.service;

import com.example.shoppingapi.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface UserService {
    Optional<String> login(String username, String password);
    User register(User newUser) throws Exception;
}
