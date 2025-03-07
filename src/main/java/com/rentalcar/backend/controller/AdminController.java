package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.CarDTO;
import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.model.Car;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.service.CarRequestService;
import com.rentalcar.backend.service.CarService;
import com.rentalcar.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CarService carService;
    private final CarRequestService carRequestService;


    public AdminController(UserService userService, CarService carService, CarRequestService carRequestService) {
        this.userService = userService;
        this.carService = carService;
        this.carRequestService = carRequestService;
    }

    @PostMapping("/add-user")
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PutMapping("/edit-user/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO userUpdated) {
        userUpdated.setId(id);
        return userService.updateUser(userUpdated);
    }

    @PostMapping("/add-car")
    public CarDTO createCar(@RequestBody CarDTO carDTO) {

        return carService.saveCar(carDTO);
    }

    @PutMapping("/edit-car/{id}")
    public CarDTO editCar(@PathVariable Long id, @RequestBody CarDTO updatedCar) {
        updatedCar.setId(id);
        return carService.updateCar(updatedCar);
    }

    @PutMapping("/manage-request/{id}")
    public CarRequestDTO manageRequest(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> requestBody
    ) throws Exception {
        String status = requestBody.get("status");
        return carRequestService.manageRequest(id, status);
    }
}
