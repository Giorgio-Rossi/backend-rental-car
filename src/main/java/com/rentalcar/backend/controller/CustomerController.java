package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.service.CarRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CarRequestService carRequestService;

    @Autowired
    public CustomerController(CarRequestService carRequestService) {
        this.carRequestService = carRequestService;
    }

    @PostMapping("/add-request")
    public CarRequestDTO addCarRequest(@RequestBody CarRequestDTO carRequestDTO) {
        return carRequestService.addRequest(carRequestDTO);
    }
}
