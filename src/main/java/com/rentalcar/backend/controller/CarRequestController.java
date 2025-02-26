package com.rentalcar.backend.controller;

import com.rentalcar.backend.service.CarRequestService;
import org.springframework.web.bind.annotation.*;

import com.rentalcar.backend.service.CarRequestService;

@RestController
@RequestMapping("/api/car-requests")
public class CarRequestController {

    @PostMapping
    public CarRequestService createCarRequest(@RequestBody CarRequestService carRequest) {
        return carRequest;
    }
}
