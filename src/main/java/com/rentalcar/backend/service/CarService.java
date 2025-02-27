package com.rentalcar.backend.service;

import java.util.List;

import com.rentalcar.backend.dto.CarDTO;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.model.Car;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public CarDTO saveCar(CarDTO car) {
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        this.carRepository.deleteById(id);
    }

    public List<CarDTO> getAllCars() {
        return this.carRepository.findAll();
    }

    public CarDTO getCarByLicensePlate(String licensePlate){
        return carRepository.findByLicensePlate(licensePlate);
    }

    public CarDTO createCar(CarDTO car) {
        return carRepository.save(car);
    }

    /*
    public Car registerCar(Long id, String brand, String model, String licensePlate, String status){
        if (carRepository.findByLicensePlate(licensePlate) != null){
            throw new RuntimeException("Car already exists!");
        }

        Car newCar = new Car();
        newCar.setBrand(brand);
        newCar.setModel(model);
        newCar.setLicensePlate(licensePlate);
        newCar.setStatus(status);

        return carRepository.save(newCar);
    }
     */

    public Car updateCar(Long id, Car updatedCar){
        return carRepository.findById(id).map(car -> {
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setLicensePlate(updatedCar.getLicensePlate());
            car.setStatus(updatedCar.getStatus());
            return saveCar(car);
        }).orElseThrow(() -> new RuntimeException("Car not found"));
    }
}
