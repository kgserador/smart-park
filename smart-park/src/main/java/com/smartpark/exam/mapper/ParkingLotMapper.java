package com.smartpark.exam.mapper;

import com.smartpark.exam.dto.ParkingLotDTO;
import com.smartpark.exam.entity.ParkingLot;
import com.smartpark.exam.entity.Vehicle;

import java.util.Set;

public class ParkingLotMapper {

    public static ParkingLotDTO mapToParkingLotDTO(ParkingLotDTO parkingLotDTO, ParkingLot parkingLot) {
        parkingLotDTO.setLotId(parkingLot.getLotId());
        parkingLotDTO.setCapacity(parkingLot.getCapacity());
        parkingLotDTO.setLocation(parkingLot.getLocation());
        parkingLotDTO.setOccupiedSpaces(parkingLot.getOccupiedSpaces());
        parkingLotDTO.setCostPerMinute(parkingLot.getCostPerMinute());
        return parkingLotDTO;
    }

    public static ParkingLot mapToParkingLot(ParkingLot parkingLot, ParkingLotDTO parkingLotDTO, Set<Vehicle> vehicles) {
        parkingLot.setLotId(parkingLotDTO.getLotId());
        parkingLot.setCapacity(parkingLotDTO.getCapacity());
        parkingLot.setLocation(parkingLotDTO.getLocation());
        parkingLot.setOccupiedSpaces(parkingLotDTO.getOccupiedSpaces());
        parkingLot.setCostPerMinute(parkingLotDTO.getCostPerMinute());
        parkingLot.setVehicle(vehicles);
        return parkingLot;
    }
}
