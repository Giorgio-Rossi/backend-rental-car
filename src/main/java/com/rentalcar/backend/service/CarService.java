package com.rentalcar.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.CarRepository;
import com.rentalcar.backend.model.Car;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        this.carRepository.deleteById(id);
    }

    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }
}
