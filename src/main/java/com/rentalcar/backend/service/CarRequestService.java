package com.rentalcar.backend.service;

import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.repository.CarRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
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

    public CarRequest manageRequest(Long requestID, CarRequest updatedStatus) throws Exception {
        return carRequestRepository.findById(requestID).map( CarRequest -> {
            CarRequest.setStatus(updatedStatus.getStatus());
            return saveCarRequest(CarRequest);
        }).orElseThrow(() -> new Exception("Request not found"));
    }

    public CarRequest addRequest(Long id, Long userID, Long carID, CarRequest.CarRequestStatus status, Date startReservation, Date endReservation, Date createdAt, Date updatedAt){
        if (carRequestRepository.findById(carID).isPresent()){
            throw new RuntimeException("Car already exists!");
        }

        CarRequest newCarRequest = new CarRequest();
        newCarRequest.setId(id);
        newCarRequest.setUserId(userID);
        newCarRequest.setCarId(carID);
        newCarRequest.setStatus(status);
        newCarRequest.setStartReservation(startReservation);
        newCarRequest.setEndReservation(endReservation);
        newCarRequest.setCreatedAt(new Date());
        newCarRequest.setUpdatedAt(new Date());

        return carRequestRepository.save(newCarRequest);

    }
}
