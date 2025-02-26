package com.rentalcar.backend.controller;

import com.rentalcar.backend.model.Car;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.service.CarService;
import com.rentalcar.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AdminController {
    @Autowired
    private UserService userService;
    private CarService carService;

    @PostMapping("/add-user")
    public User registerUser(Long id, String username, String email, String role, String password, String fullName) {

        return userService.registerUser(id, username, email, role, password, fullName);
    }

    @PostMapping("/edit-user")
    public User editUser(@PathVariable Long id, @RequestBody User userUpdated){
        return userService.updateUser(id, userUpdated);
    }

    @PostMapping("/add-car")
    public Car registerCar(Long id, String brand, String model, String licensePlate, String status){
        return carService.registerCar(id, brand, model, licensePlate, status);
    }

    @PostMapping("/update-car")
    public Car registerCar(@PathVariable Long id, @RequestBody Car updatedCar){
        return carService.updateCar(id, updatedCar);
    }
}
