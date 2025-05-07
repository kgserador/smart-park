package com.smartpark.exam.mapper;

import com.smartpark.exam.dto.VehicleDTO;
import com.smartpark.exam.entity.ParkingLot;
import com.smartpark.exam.entity.Vehicle;

public class VehicleMapper {

    public static VehicleDTO mapToVehiclDTO(VehicleDTO vehicleDTO, Vehicle vehicle, Integer lotId) {
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setType(vehicle.getType());
        vehicleDTO.setLotId(lotId);
        vehicleDTO.setOwnerName(vehicle.getOwnerName());
        return vehicleDTO;
    }

    public static Vehicle mapToVehicle(Vehicle vehicle, VehicleDTO vehicleDTO, ParkingLot parkingLot) {
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setOwnerName(vehicleDTO.getOwnerName());
        vehicle.setParkingLot(parkingLot);
        return vehicle;
    }
}
