package com.rentalcar.backend.dto;

import com.rentalcar.backend.model.CarRequest;

import java.util.Date;

public class CarRequestDTO {
    private Long id;
    private Long userID;
    private Long carID;
    private CarRequest.CarRequestStatus status;
    private Date startReservation;
    private Date endReservation;
    private Date createdAt;
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public CarRequest.CarRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CarRequest.CarRequestStatus status) {
        this.status = status;
    }

    public Date getStartReservation() {
        return startReservation;
    }

    public void setStartReservation(Date startReservation) {
        this.startReservation = startReservation;
    }

    public Date getEndReservation() {
        return endReservation;
    }

    public void setEndReservation(Date endReservation) {
        this.endReservation = endReservation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CarRequestDTO() {
    }

    public CarRequestDTO(Long id, Long userID, Long carID, CarRequest.CarRequestStatus status, Date startReservation, Date endReservation, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userID = userID;
        this.carID = carID;
        this.status = status;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
