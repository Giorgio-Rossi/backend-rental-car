package com.rentalcar.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Date;

@Getter
public class CarRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Long carId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarRequestStatus status;

    @Column(nullable = false)
    private Date startReservation;

    @Column(nullable = false)
    private Date endReservation;

    private Date createdAt;
    private Date updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setStatus(CarRequestStatus  status) {
        this.status = status;
    }

    public void setStartReservation(Date startReservation) {
        this.startReservation = startReservation;
    }

    public void setEndReservation(Date endReservation) {
        this.endReservation = endReservation;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CarRequest() {
    }

    public CarRequest(Long id, Long userId, Long carId, CarRequestStatus  status, Date startReservation, Date endReservation, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.status = status;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum CarRequestStatus {
        APPROVATA,
        CANCELLATA,
        ANNULLATA
    }

}
