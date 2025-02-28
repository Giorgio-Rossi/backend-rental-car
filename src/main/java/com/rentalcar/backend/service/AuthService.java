package com.rentalcar.backend.service;

/*
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
   // private final BCryptPasswordEncoder passwordEncoder;

    private final Set<String> activeUsers = new HashSet<>();

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    private static final String SECRET_KEY = "test";
/*
   public String login(String username, String password) {
       User user = userRepository.findByUsername(username);

       if(user != null && passwordEncoder.matches(password, user.getPassword())){
           return "Login successful. Welcome, " + user.getUsername();
       }
       return "Invalid username or password";
   }


   public String login(String username, String password) {
       User user = userRepository.findByUsername(username);

       if(user != null && userRepository.findByPassword(password)!=null){
           return "Login successful. Welcome, " + user.getUsername();
       }
       return "Invalid username or password";
   }
    public String logout(String username){
        if(activeUsers.contains(username)){
            activeUsers.remove(username);
            return "Logout successul for user: " + username;
        } else {
            return "User not logged in:" + activeUsers;
        }
    }

    private String generateJwtToken(String username) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }

}
        */