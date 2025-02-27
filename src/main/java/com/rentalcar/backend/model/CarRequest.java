package com.rentalcar.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Date;

@Setter
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
