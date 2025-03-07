package com.rentalcar.backend.service;

import com.rentalcar.backend.dto.CarDTO;
import com.rentalcar.backend.dto.CarRequestDTO;
import com.rentalcar.backend.model.Car;
import com.rentalcar.backend.model.CarRequest;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.repository.CarRequestRepository;
import com.rentalcar.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarRequestService {

    private final CarRequestRepository carRequestRepository;
    private final UserRepository userRepository;
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

    public List<CarDTO> getAvailableCars(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<CarDTO> allCars = carRepository.findAll().stream()
                .map(this::convertToCarDTO)
                .toList();

        List<CarRequestDTO> carRequests = carRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return allCars.stream()
                .filter(car -> isCarAvailable(car, carRequests, start, end))
                .collect(Collectors.toList());
    }

    private boolean isCarAvailable(CarDTO car, List<CarRequestDTO> carRequests, LocalDate start, LocalDate end) {
        for (CarRequestDTO request : carRequests) {
            if (request.getCarID().equals(car.getId())) {
                LocalDate requestStart = LocalDate.parse(request.getStartReservation().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate requestEnd = LocalDate.parse(request.getEndReservation().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if ((start.isBefore(requestEnd) && end.isAfter(requestStart))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<CarRequestDTO> getAllCarRequests() {
        return carRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
        System.out.println("Car ID: " + carRequestDTO.getCarID());
        System.out.println("User ID: " + carRequestDTO.getUserID());

        List<CarRequest> overlappingRequests = carRequestRepository.findActiveOverlappingRequests(
                carRequestDTO.getCarID(),
                carRequestDTO.getUserID(),
                carRequestDTO.getStartReservation(),
                carRequestDTO.getEndReservation()
        );

        if (!overlappingRequests.isEmpty()) {
            throw new RuntimeException("Esiste già una richiesta attiva per quest'auto in queste date!");
        }

        CarRequest carRequest = convertToEntity(carRequestDTO);
        carRequest.setCreatedAt(new Date());
        carRequest.setUpdatedAt(new Date());

        return convertToDTO(carRequestRepository.save(carRequest));
    }

    public Long getLastRequestId() {
        return carRequestRepository.findTopByOrderByIdDesc().map(CarRequest::getId).orElse(0L);
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

    private CarDTO convertToCarDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getLicensePlate(),
                car.getStatus(),
                car.getUpdatedAt()
        );
    }
}

