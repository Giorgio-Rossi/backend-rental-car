package com.rentalcar.backend.controller;

import com.rentalcar.backend.model.Car;
import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.service.CarRequestService;
import com.rentalcar.backend.service.CarService;
import com.rentalcar.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    private CarService carService;
    private CarRequestService carRequestSerivce;

    @PostMapping("/add-user")
    public User registerUser(Long id, String username, String email, String role, String password, String fullName) {
        return userService.registerUser(id, username, email, role, password, fullName);
    }

    @PostMapping("/edit-user")
    public User editUser(@PathVariable Long id, @RequestBody User userUpdated){
        return userService.updateUser(id, userUpdated);
    }

    /*
    @PostMapping("/add-car")
    public Car registerCar(Long id, String brand, String model, String licensePlate, String status){
        return carService.registerCar(id, brand, model, licensePlate, status);
    }
    */

    @PostMapping("/add-car")
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }


    @PostMapping("/edit-car")
    public Car editCar(@PathVariable Long id, @RequestBody Car updatedCar){
        return carService.updateCar(id, updatedCar);
    }

    @PostMapping("/manage-request")
    public CarRequest manageRequest(@PathVariable Long id,@RequestBody CarRequest status) throws Exception {
        return carRequestSerivce.manageRequest(id, status);
    }

}
