package com.example.shoppingapi.config;


import com.example.shoppingapi.utils.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTFilter jwtFilter;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/**", "user/**").permitAll()
                .antMatchers("/order/**", "/cart/**", "/test/secure").authenticated()
                .and().cors()
                .and().csrf().disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"))
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET"))
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600"))
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "true"))
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization"))
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization"));

        // MARK: Should I use addFilterAfter? Seems JwtFilter generate JWT before userLogin
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
