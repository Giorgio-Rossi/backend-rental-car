package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.CarDTO;
import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.dto.UserDTO;
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

    @Autowired
    private CarService carService;

    @Autowired
    private CarRequestService carRequestService;

    @PostMapping("/add-user")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/edit-user/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO userUpdated) {
        userUpdated.setId(id);
        return userService.updateUser(userUpdated);
    }

    @PostMapping("/add-car")
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

    @PostMapping("/edit-car/{id}")
    public CarDTO editCar(@PathVariable Long id, @RequestBody CarDTO updatedCar) {
        updatedCar.setId(id);
        return carService.updateCar(updatedCar);
    }

    @PostMapping("/manage-request/{id}")
    public CarRequestDTO manageRequest(@PathVariable Long id, @RequestBody CarRequestDTO status) throws Exception {
        return carRequestService.manageRequest(id, status);
    }
}
