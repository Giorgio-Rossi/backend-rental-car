package com.rentalcar.backend.controller;

import com.rentalcar.backend.model.User;
import com.rentalcar.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public User registerUser(Long id, String username, String email, String role, String password, String fullName) {

        return userService.registerUser(id, username, email, role, password, fullName);
    }
}
