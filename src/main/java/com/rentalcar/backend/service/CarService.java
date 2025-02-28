package com.rentalcar.backend.service;

import java.util.List;
import com.rentalcar.backend.dto.CarDTO;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.model.Car;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public CarDTO saveCar(CarDTO carDTO) {
        Car car = convertToEntity(carDTO);
        return convertToDTO(carRepository.save(car));
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CarDTO getCarByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public CarDTO createCar(CarDTO carDTO) {
        return saveCar(carDTO);
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
        car.setId(carDTO.getId());
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setStatus(carDTO.getStatus());
        car.setUpdatedAt(carDTO.getUpdatedAt());
        return car;
    }
}
