package com.smartpark.exam.service;

import com.smartpark.exam.dto.ParkingCostDTO;
import com.smartpark.exam.dto.ParkingLotDTO;
import com.smartpark.exam.dto.VehicleDTO;

import java.util.List;


public interface IParkingService {

    void registerParkingLot(ParkingLotDTO parkingLotDTO);

    void registerVehicle(VehicleDTO vehicleDTO);

    boolean checkInVehicle(VehicleDTO vehicleDTO);

    ParkingCostDTO checkOutVehicle(VehicleDTO vehicleDTO);

    ParkingLotDTO getParkingLotDetails(Integer lotId);

    List<VehicleDTO> getAllVehiclesOfParkingLot(Integer lotId);


}
