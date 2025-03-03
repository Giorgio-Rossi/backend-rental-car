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

    @PostMapping("/create-request")
    public CarRequestDTO createCarRequest(@RequestBody CarRequestDTO  CarRequestDTO) {
       return carRequestService.addRequest(CarRequestDTO);
    }

    @GetMapping("/all-requests")
    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestService.getAllCarRequests();
    }

    @PutMapping("/update-request/{id}")
    public CarRequestDTO updateRequest(
            @PathVariable("id") Long id,
            @RequestBody CarRequestDTO carRequestDTO
    ) {
        carRequestDTO.setId(id);
        return carRequestService.updateRequest(carRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable("id") Long id) {
        carRequestService.deleteRequest(id);
    }
}
