package com.smartpark.exam.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingCostDTO {

    private String licensePlate;
    private Integer lotId;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Double totalCost;

}
