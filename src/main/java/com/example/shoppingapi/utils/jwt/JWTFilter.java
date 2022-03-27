package com.example.shoppingapi.utils.jwt;

import com.example.shoppingapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    private Optional<String> getJWT(HttpServletRequest request) {
        String headerAuthorization =  request.getHeader("Authorization");
        if (headerAuthorization == null) {
            return Optional.empty();
        }

        if (
                headerAuthorization.isEmpty()
                        || headerAuthorization.isBlank()
                        || !headerAuthorization.startsWith("Bearer ")
        ) {
            return Optional.empty();
        }

        return Optional.of(headerAuthorization.substring(7));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> ClientJWTOptional = getJWT(request);

        if (ClientJWTOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientJWT = ClientJWTOptional.get();
        Jws<Claims> parsedJWT = jwtUtil.parseToken(clientJWT.substring(7));
        boolean isJWTNonExpired = parsedJWT.getBody().getExpiration().after(new Date());
        String username = parsedJWT.getBody().getSubject();
        List<String> userRoles = parsedJWT.getBody().get("userRoles", List.class);
        List<SimpleGrantedAuthority> userAuthorities = userRoles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        if (isJWTNonExpired) {
            var newToken = jwtUtil.createToken(username, userRoles);
            response.setHeader("Authorization", newToken);
        }

        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authAfterSuccessLogin = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        authAfterSuccessLogin.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);

        filterChain.doFilter(request, response);
    }
}
