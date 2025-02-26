package com.rentalcar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalcar.backend.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    List<Car> findByModel(String model);
    List<Car> findByBrand(String brand);
    List<Car> findByAvailableTrue();
    List<Car> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Car> findByOrderByPriceAsc();
    List<Car> findByModelContaining(String keyword);

    Car findByLicensePlate(String licensePlate);
}
