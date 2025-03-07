package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.LoginRequestDTO;
import com.rentalcar.backend.dto.LoginResponseDTO;
import com.rentalcar.backend.service.CustomUserDetailsService;
import com.rentalcar.config.JwtUtils;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailService,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
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
}
