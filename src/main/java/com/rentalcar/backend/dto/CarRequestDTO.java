package com.rentalcar.backend.dto;

import com.rentalcar.backend.model.CarRequest;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CarRequestDTO {
    private Long id;
    private Long userID;
    private Long carID;
    private CarRequest.CarRequestStatus status;
    private Date startReservation;
    private Date endReservation;
    private Date createdAt;
    private Date updatedAt;

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

