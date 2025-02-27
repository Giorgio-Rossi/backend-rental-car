package com.rentalcar.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private String licensePlate;
    private String status;
    private LocalDateTime updatedAt;

    public CarDTO() {}

    public CarDTO(Long id, String brand, String model, String licensePlate, String status, LocalDateTime updatedAt) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
