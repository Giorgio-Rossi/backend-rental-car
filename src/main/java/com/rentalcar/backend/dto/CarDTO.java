package com.rentalcar.backend.dto;

import java.time.LocalDateTime;

public class CarDTO {
    private Long id;
    private Long brand;
    private Long model;
    private Long licensePlate;
    private String status;
    private LocalDateTime updatedAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(Long licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getModel() {
        return model;
    }

    public void setModel(Long model) {
        this.model = model;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public CarDTO() {}

    public CarDTO(Long id, Long brand, Long model, Long licensePlate, String status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.status = status;
    }
}
