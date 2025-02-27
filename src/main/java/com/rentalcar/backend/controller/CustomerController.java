package com.rentalcar.backend.controller;

import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.service.CarRequestService;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

public class CustomerController{

    private CarRequest carRequest;
    private CarRequestService carRequestService;

    @PostMapping("/add-request")
    public CarRequest addCarRequest(Long id, Long userID, Long carID, CarRequest.CarRequestStatus status, Date startReservation, Date endReservation, Date createdAt, Date updatedAt){
        return carRequestService.addRequest(id, userID, carID, status, startReservation, endReservation, createdAt, updatedAt);
    }
}
