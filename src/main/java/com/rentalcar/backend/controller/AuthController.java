package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.LoginRequestDTO;
import com.rentalcar.backend.dto.LoginResponseDTO;
import com.rentalcar.backend.service.CustomUserDetailsService;
import com.rentalcar.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequestDTO.getUsername());
        String token = this.jwtService.generateToken(userDetails);

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            jwtService.invalidateToken(token);
            return ResponseEntity.ok("Logout effettuato con successo.");
        }
        return ResponseEntity.badRequest().body("Token non valido.");
    }
}
