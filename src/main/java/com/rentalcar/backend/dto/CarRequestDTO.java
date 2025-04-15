package com.rentalcar.backend.dto;

import com.rentalcar.backend.model.CarRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDTO {
    private Long id;
    private Long userID;
    private Long carID;
    private CarRequest.CarRequestStatus status;
    private Date startReservation;
    private Date endReservation;
    private Date createdAt;
    private Date updatedAt;
}

