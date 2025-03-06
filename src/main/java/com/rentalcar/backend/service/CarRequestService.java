package com.rentalcar.backend.service;

import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.model.Car;
import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.repository.CarRequestRepository;
import com.rentalcar.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarRequestService {


    private final CarRequestRepository carRequestRepository;

    private  final UserRepository userRepository;

    private final CarRepository carRepository;

    public CarRequestService(CarRequestRepository carRequestRepository, UserRepository userRepository, CarRepository carRepository) {
        this.carRequestRepository = carRequestRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }


    public List<CarRequestDTO> getCarRequestsByUserId(String username) {
        return carRequestRepository.findByUserUsername(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CarRequestDTO saveCarRequest(CarRequestDTO carRequestDTO) {
        CarRequest carRequest = convertToEntity(carRequestDTO);
        return convertToDTO(carRequestRepository.save(carRequest));
    }

    public CarRequestDTO updateRequest(CarRequestDTO carRequestDTO) {
        CarRequest carRequest = convertToEntity(carRequestDTO);
        carRequest.setUpdatedAt(new Date());
        return convertToDTO(carRequestRepository.save(carRequest));
    }

    public CarRequestDTO manageRequest(Long requestID, String newStatus) throws Exception {
        return carRequestRepository.findById(requestID).map(carRequest -> {
            CarRequest.CarRequestStatus status = CarRequest.CarRequestStatus.valueOf(newStatus);
            carRequest.setStatus(status);
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

    @Transactional
    public void deleteRequest(Long requestID) {
         carRequestRepository.deleteById(requestID);
    }

    private CarRequestDTO convertToDTO(CarRequest carRequest) {
        return new CarRequestDTO(
                carRequest.getId(),
                carRequest.getUser().getId(),
                carRequest.getCar().getId(),
                carRequest.getStatus(),
                carRequest.getStartReservation(),
                carRequest.getEndReservation(),
                carRequest.getCreatedAt(),
                carRequest.getUpdatedAt()
        );
    }

    private CarRequest convertToEntity(CarRequestDTO carRequestDTO) {
        User user = userRepository.findById(carRequestDTO.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Car car = carRepository.findById(carRequestDTO.getCarID())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        CarRequest carRequest = new CarRequest();
        carRequest.setId(carRequestDTO.getId());
        carRequest.setUser(user);
        carRequest.setCar(car);
        carRequest.setStatus(carRequestDTO.getStatus());
        carRequest.setStartReservation(carRequestDTO.getStartReservation());
        carRequest.setEndReservation(carRequestDTO.getEndReservation());
        carRequest.setCreatedAt(carRequestDTO.getCreatedAt());
        carRequest.setUpdatedAt(carRequestDTO.getUpdatedAt());

        return carRequest;
    }
}
