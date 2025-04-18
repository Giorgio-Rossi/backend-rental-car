package com.rentalcar.backend.service;

import java.util.List;
import com.rentalcar.backend.dto.CarDTO;
import com.rentalcar.backend.repository.CarRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.model.Car;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarRequestRepository carRequestRepository;

    public CarService(CarRepository carRepository, CarRequestRepository carRequestRepository) {
        this.carRepository = carRepository;
        this.carRequestRepository = carRequestRepository;
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public CarDTO saveCar(CarDTO carDTO) {
        Car car = convertToEntity(carDTO);
        return convertToDTO(carRepository.save(car));
    }

    @Transactional
    public void deleteCar(Long id) {
        try {
            Optional<Car> carOpt = carRepository.findById(id);
            if (carOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto non trovata");
            }
            System.out.println("Deleting car requests for car ID: " + id);
            Car car = carOpt.get();

            carRequestRepository.deleteByCarId(id);
            carRepository.delete(car);

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto non trovata");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile eliminare l'auto, potrebbe avere dati associati");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante l'eliminazione dell'auto");
        }
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CarDTO updateCar(CarDTO carDTO) {
        return carRepository.findById(carDTO.getId()).map(car -> {
            car.setBrand(carDTO.getBrand());
            car.setModel(carDTO.getModel());
            car.setLicensePlate(carDTO.getLicensePlate());
            car.setStatus(carDTO.getStatus());
            car.setUpdatedAt(carDTO.getUpdatedAt());
            return convertToDTO(carRepository.save(car));
        }).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    private CarDTO convertToDTO(Car car) {
        return new CarDTO(car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate(), car.getStatus(), car.getUpdatedAt());
    }

    private Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setStatus(carDTO.getStatus());
        car.setUpdatedAt(carDTO.getUpdatedAt());
        return car;
    }
}
