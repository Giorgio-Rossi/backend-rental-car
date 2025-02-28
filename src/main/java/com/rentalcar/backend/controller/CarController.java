package com.rentalcar.backend.controller;

import java.util.List;

import com.rentalcar.backend.dto.CarDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalcar.backend.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/allcars")
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }


    @GetMapping("/test")
    public String test() {
        return "Endpoint di test raggiunto!";
    }

    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
