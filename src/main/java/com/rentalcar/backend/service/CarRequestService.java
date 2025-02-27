package com.rentalcar.backend.service;

import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.repository.CarRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarRequestService {

    @Autowired
    private CarRequestRepository carRequestRepository;

    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CarRequestDTO saveCarRequest(CarRequestDTO carRequestDTO) {
        CarRequest carRequest = convertToEntity(carRequestDTO);
        return convertToDTO(carRequestRepository.save(carRequest));
    }

    public CarRequestDTO manageRequest(Long requestID, CarRequestDTO updatedStatus) throws Exception {
        return carRequestRepository.findById(requestID).map(carRequest -> {
            carRequest.setStatus(updatedStatus.getStatus());
            return convertToDTO(carRequestRepository.save(carRequest));
        }).orElseThrow(() -> new Exception("Request not found"));
    }

    public CarRequestDTO addRequest(CarRequestDTO carRequestDTO) {
        if (carRequestRepository.findByCarIdAndUserId(carRequestDTO.getCarID(), carRequestDTO.getUserID()).isPresent()) {
            throw new RuntimeException("Request already exists for this car!");
        }
        CarRequest carRequest = convertToEntity(carRequestDTO);
        carRequest.setCreatedAt(new Date());
        carRequest.setUpdatedAt(new Date());

        return convertToDTO(carRequestRepository.save(carRequest));
    }


    private CarRequestDTO convertToDTO(CarRequest carRequest) {
        return new CarRequestDTO(
                carRequest.getId(),
                carRequest.getUserId(),
                carRequest.getCarId(),
                carRequest.getStatus(),
                carRequest.getStartReservation(),
                carRequest.getEndReservation(),
                carRequest.getCreatedAt(),
                carRequest.getUpdatedAt()
        );
    }

    private CarRequest convertToEntity(CarRequestDTO carRequestDTO) {
        return new CarRequest(
                carRequestDTO.getId(),
                carRequestDTO.getUserID(),
                carRequestDTO.getCarID(),
                carRequestDTO.getStatus(),
                carRequestDTO.getStartReservation(),
                carRequestDTO.getEndReservation(),
                carRequestDTO.getCreatedAt(),
                carRequestDTO.getUpdatedAt()
        );
    }
}
