package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.AdminUpdateDTO;
import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/alluser")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody AdminUpdateDTO admin) {
         userService.adminUpdateUser(id, admin);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/get-user-by-username")
    public UserDTO getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }
}
