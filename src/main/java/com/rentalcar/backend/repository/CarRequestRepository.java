package com.rentalcar.backend.repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalcar.backend.model.CarRequest;

public interface CarRequestRepository extends JpaRepository<CarRequest, Long> {

}
