package com.rentalcar.backend.service;

import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.repository.CarRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarRequestService {

    @Autowired
    private CarRequestRepository carRequestRepository;

    public List<CarRequest> getAllCarRequest(){
        return carRequestRepository.findAll();
    }

    public CarRequest saveCarRequest(CarRequest carRequest) {
        return carRequest;
    }
}
