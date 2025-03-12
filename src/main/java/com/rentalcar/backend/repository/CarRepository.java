package com.rentalcar.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rentalcar.backend.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    List<Car> findByStatus(String status);
}
