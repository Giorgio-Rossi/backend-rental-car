package com.rentalcar.backend.controller;

import com.rentalcar.backend.dto.CarDTO;
import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.service.CarRequestService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/car-requests")
public class CarRequestController {

    private final CarRequestService carRequestService;

    public CarRequestController(CarRequestService carRequestService) {
        this.carRequestService = carRequestService;
    }

    @GetMapping("/available-cars")
    public List<CarDTO> getAvailableCars(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        return carRequestService.getAvailableCars(startDate, endDate);
    }

    @GetMapping("/all-requests")
    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestService.getAllCarRequests();
    }

    @PatchMapping("/update-request/{id}")
    public CarRequestDTO updateRequest(
            @PathVariable("id") Long id,
            @RequestBody CarRequestDTO carRequestDTO
    ) {
        carRequestDTO.setId(id);
        return carRequestService.updateRequest(carRequestDTO);
    }

    @GetMapping("get-request-by-username")
    public List<CarRequestDTO> getCarRequestsByUser(@RequestParam String username) {
        return carRequestService.getCarRequestsByUsername(username);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable("id") Long id) {
        carRequestService.deleteRequest(id);
    }

    @GetMapping("/{id}")
    public CarRequestDTO getRequest(@PathVariable("id") Long id) {
        return carRequestService.getRequest(id);
    }


}
