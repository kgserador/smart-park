package com.smartpark.exam.dto;

import lombok.Data;

@Data
public class ParkingLotDTO {

    private Integer lotId;

    private String location;

    private Integer capacity;

    private Integer occupiedSpaces;

    private Double costPerMinute;
}
