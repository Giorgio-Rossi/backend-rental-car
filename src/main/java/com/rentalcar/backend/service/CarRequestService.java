package com.rentalcar.backend.service;

import com.rentalcar.backend.model.CarRequest;
import org.springframework.stereotype.Service;

@Service
public class CarRequestService {

    public CarRequest saveCarRequest(CarRequest carRequest) {
        return carRequest;
    }
}
