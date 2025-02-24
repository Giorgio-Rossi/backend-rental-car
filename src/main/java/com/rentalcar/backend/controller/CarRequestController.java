package com.rentalcar.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.rentalcar.backend.service.CarRequest;

@RestController
@RequestMapping("/api/carrequests")
public class CarRequestController {

    @PostMapping
    public CarRequest createCarRequest(@RequestBody CarRequest carRequest) {
        return carRequest;
    }
}
