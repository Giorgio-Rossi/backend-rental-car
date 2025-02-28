package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.service.CarRequestService;
import org.springframework.web.bind.annotation.*;

import com.rentalcar.backend.service.CarRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/car-requests")
public class CarRequestController {

    private final CarRequestService carRequestService;

    public CarRequestController(CarRequestService carRequestService) {
        this.carRequestService = carRequestService;
    }

    @PostMapping
    public CarRequestService createCarRequest(@RequestBody CarRequestService carRequest) {
        return carRequest;
    }

    @GetMapping("/all-requests")
    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestService.getAllCarRequests();
    }
}
