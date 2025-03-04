package com.rentalcar.backend.service;


import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final Set<String> activeUsers = new HashSet<>();

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            activeUsers.add(username);
            return user;
        }
        return null;
    }

    public String logout(String username){
        if(activeUsers.contains(username)){
            activeUsers.remove(username);
            return "Logout successful for user: " + username;
        } else {
            return "User not logged in: " + username;
        }
    }

    private String generateJwtToken(String username) {
        Key key = new SecretKeySpec("test".getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }
}

