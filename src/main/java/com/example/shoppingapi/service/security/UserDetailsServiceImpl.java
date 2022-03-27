package com.example.shoppingapi.service.security;

import com.example.shoppingapi.dao.UserRepository;
import com.example.shoppingapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> savedUser = userRepository.findByUsername(username);

        if (savedUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        return savedUser.get();
    }
}
