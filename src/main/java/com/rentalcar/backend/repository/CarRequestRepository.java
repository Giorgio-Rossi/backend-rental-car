package com.rentalcar.backend.repository;

import com.rentalcar.backend.dto.CarRequestDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalcar.backend.model.CarRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRequestRepository extends JpaRepository<CarRequest, Long> {
    Optional<CarRequest> findByCarIdAndUserId(Long carId, Long userId);
    void deleteByUserId(Long userId);
    List<CarRequest> findByUserUsername(String username);


    @Modifying
    @Transactional
    @Query("delete from CarRequest cr where cr.car.id = :id")
    void deleteByCarId(@Param("id") Long id);

}
