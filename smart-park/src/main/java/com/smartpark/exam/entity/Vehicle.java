package com.smartpark.exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="VEHICLE")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vehicle extends BaseEntity {

    @Id
    @Column(name = "licensePlate",unique = true, nullable = false)
    private String licensePlate;
    private String type;
    private String ownerName;

    @ManyToOne
    @JoinColumn(name="lot_id")
    private ParkingLot parkingLot;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
