package com.rentalcar.backend.repository;

import com.rentalcar.backend.dto.CarRequestDTO;
import jakarta.transaction.Transactional;

import java.util.Date;
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
    void deleteByUserId(Long userId);
    List<CarRequest> findByUserUsername(String username);
    Optional<CarRequest> findTopByOrderByIdDesc();

    @Query("SELECT cr FROM CarRequest cr WHERE cr.car.id = :carId AND cr.user.id = :userId " +
            "AND :startReservation < cr.endReservation AND :endReservation > cr.startReservation " +
            "AND cr.status NOT IN ('CANCELLATA', 'ANNULLATA')")
    List<CarRequest> findActiveOverlappingRequests(@Param("carId") Long carId,
                                                   @Param("userId") Long userId,
                                                   @Param("startReservation") Date startReservation,
                                                   @Param("endReservation") Date endReservation);

    @Modifying
    @Transactional
    @Query("delete from CarRequest cr where cr.car.id = :id")
    void deleteByCarId(@Param("id") Long id);

}
