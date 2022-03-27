package com.example.shoppingapi.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    private String PRIVATE_KEY = "a3NmaGdzZGtoamZzZ2JranNkaGZka2hiZGZzZ2hrZmRramhkc2drZHNoZ2Rza2pzZg==";

    public String createToken(String username, List<String> userRoles) {
        int hoursToExpire = 10;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(hoursToExpire, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .addClaims(Map.of("userRoles", userRoles))
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
    }

    public List<SimpleGrantedAuthority> parseUserAuthorities(Jws<Claims> parsedToken) {
        List<String> jwsUserRoles = parsedToken.getBody().get("userRoles", List.class);

        return jwsUserRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
