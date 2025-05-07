package com.smartpark.exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="PARKING_LOT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ParkingLot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lotId",length=50, unique = true, nullable = false, insertable = false, updatable = false)
    private Integer lotId;

    private String location;

    private Integer capacity;

    private Integer occupiedSpaces;

    private Double costPerMinute;

    @OneToMany(mappedBy="parkingLot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Vehicle> vehicle;


}
