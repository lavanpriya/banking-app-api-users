package com.app.banking_system.api.users.banking_app_api_users.security;

import com.app.banking_system.api.users.banking_app_api_users.service.UsersService;
import com.app.banking_system.api.users.banking_app_api_users.ui.model.LoginRequestModel;
import com.app.banking_system.api.users.banking_app_api_users.ui.model.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsersService usersService;
    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UsersService usersService, Environment environment){
        super(authenticationManager);
        this.environment = environment;
        this.usersService = usersService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestModel creds =  new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

        return getAuthenticationManager().authenticate( new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain
            , Authentication auth) throws IOException, ServletException {

        String userName =  ((User)auth.getPrincipal()).getUsername();
        UserDto userDetails = usersService.getUserDetailsByEmail(userName);

        Instant now =  Instant.now();

        String tokenSecret = environment.getProperty("token.secret");
    // Generate random bytes

        byte[] secretKeyBytes = Base64.getEncoder().encode(Objects.requireNonNull(tokenSecret).getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes,"HmacSHA512");
       // SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyBytes));

        System.out.println(environment.getProperty("token.expiration_time"));
        String token = Jwts.builder().subject(String.valueOf(userDetails.getCustomerId()))
                .expiration(Date.from(now.plusMillis(Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time"))))))
                .issuedAt(Date.from(now))
                .signWith(secretKey).compact();

        response.addHeader("token",token);
        response.addHeader("userId", String.valueOf(userDetails.getCustomerId()));
    }

}
