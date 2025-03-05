package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.LoginRequestDTO;
import com.rentalcar.backend.dto.LoginResponseDTO;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.service.AuthService;
import com.rentalcar.backend.service.CustomUserDetailService;
import com.rentalcar.config.JwtUtils;
import com.rentalcar.security.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            AuthService authService,
            CustomUserDetailService customUserDetailService,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequestDTO.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
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
