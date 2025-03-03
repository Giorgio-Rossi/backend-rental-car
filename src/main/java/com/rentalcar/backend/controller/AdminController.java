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

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final CarService carService;
    private final CarRequestService carRequestService;
    private final CarRepository carRepository;


    public AdminController(UserService userService, CarService carService, CarRequestService carRequestService, CarRepository carRepository, CarRepository carRepository1) {
        this.userService = userService;
        this.carService = carService;
        this.carRequestService = carRequestService;
        this.carRepository = carRepository1;
    }

    @GetMapping("/test")
    public String adminTest() {
        return "Admin controller funziona!";
    }


    @PostMapping("/add-user")
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PostMapping("/edit-user/{id}")
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO userUpdated) {
        userUpdated.setId(id);
        return userService.updateUser(userUpdated);
    }

    @PostMapping("/add-car")
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        Long lastId = carRepository.findTopByOrderByIdDesc()
                .map(Car::getId)
                .orElse(0L);

        carDTO.setId(lastId + 1);

        return carService.saveCar(carDTO);
    }

    @GetMapping("/last-car-id")
    public Long getLastCarId() {
        return carService.getLastCarId();
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
