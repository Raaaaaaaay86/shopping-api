package com.example.shoppingapi.service;

import com.example.shoppingapi.dao.UserRepository;
import com.example.shoppingapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Boolean login(String username, String password) {
        return null;
    }

    @Override
    public User register(User newUser) throws Exception {
        if (newUser.getUsername() == null || newUser.getUsername().length() == 0) {
            throw new Exception("Username is not valid");
        }
        var savedUserOptional = userRepository.findByUsername(newUser.getUsername());

        if (savedUserOptional.isPresent()) {
            throw new Exception("Username already exists");
        }

        return userRepository.save(newUser);
    }
}
