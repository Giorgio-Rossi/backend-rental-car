package com.rentalcar.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "car_request")
public class CarRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

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

    public CarRequest(Long id, User userId, Car carId, CarRequestStatus status, Date startReservation, Date endReservation, Date createdAt, Date updatedAt) {
        this.id = id;
        this.user = userId;
        this.car = carId;
        this.status = status;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @Getter
    public enum CarRequestStatus {
        APPROVATA,
        RIFIUTATA,
        ANNULLATA,
        CANCELLATA,
        IN_ATTESA;

        @Override
        public String toString() {
            return switch (this) {
                case IN_ATTESA -> "IN ATTESA";
                default -> name();
            };
        }
    }
}
