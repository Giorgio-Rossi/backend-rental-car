package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalcar.Model.CarRequest;

public interface CarRequestRepository extends JpaRepository<CarRequest, Long> {
    
}
