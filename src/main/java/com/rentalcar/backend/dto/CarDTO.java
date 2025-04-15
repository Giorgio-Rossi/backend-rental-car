package com.rentalcar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private String licensePlate;
    private String status;
    private LocalDateTime updatedAt;
}
