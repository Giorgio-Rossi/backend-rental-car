package com.rentalcar.backend.controller;

import com.rentalcar.backend.model.User;
import com.rentalcar.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User user = authService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String body) {
        String username = extractUsernameFromBody(body);
        String response = authService.logout(username);
        if (response.contains("successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private String extractUsernameFromBody(String body) {
        String[] params = body.split("&");
        for (String param : params) {
            if (param.startsWith("username=")) {
                return param.split("=")[1];
            }
        }
        return null;
    }
}
