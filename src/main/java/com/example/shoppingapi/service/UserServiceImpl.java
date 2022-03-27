package com.example.shoppingapi.service;

import com.example.shoppingapi.dao.UserRepository;
import com.example.shoppingapi.entity.User;
import com.example.shoppingapi.entity.UserRole;
import com.example.shoppingapi.utils.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Optional<String> login(String username, String password) {
        var authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (!authentication.isAuthenticated()) {
            return Optional.empty();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var roles = ((UserDetails) authentication.getPrincipal())
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var newJWTToken = jwtUtil.createToken(username, roles);

        return Optional.of(newJWTToken);
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
        var userRole = new UserRole("ROLE_USER", "user");
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setRoles(List.of(userRole));

        return userRepository.save(newUser);
    }
}
