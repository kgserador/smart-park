package com.smartpark.exam.controller;

import com.smartpark.exam.constant.SmartParkConstant;
import com.smartpark.exam.dto.ParkingCostDTO;
import com.smartpark.exam.dto.ParkingLotDTO;
import com.smartpark.exam.dto.ResponseDto;
import com.smartpark.exam.dto.VehicleDTO;
import com.smartpark.exam.service.IParkingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/smartpark/services")
@AllArgsConstructor
public class ParkingController {

    private final IParkingService parkingService;

    @PostMapping("/parking-lot/register")
    public ResponseEntity<ResponseDto> registerParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        parkingService.registerParkingLot(parkingLotDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(SmartParkConstant.STATUS_201, SmartParkConstant.PL_STATUS_MSG_201));
    }

    @PostMapping("/vehicle/register")
    public ResponseEntity<ResponseDto> registerVehicle(@RequestBody VehicleDTO vehicleDTO) {
        parkingService.registerVehicle(vehicleDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(SmartParkConstant.STATUS_201, SmartParkConstant.V_STATUS_MSG_201));
    }

    @PutMapping("/parking/checkin")
    public ResponseEntity<ResponseDto> checkInVehicle(@RequestBody VehicleDTO vehicleDTO) {
        boolean isOpen = parkingService.checkInVehicle(vehicleDTO);
        if(isOpen) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(SmartParkConstant.STATUS_200, SmartParkConstant.STATUS_MSG_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(SmartParkConstant.STATUS_417, SmartParkConstant.STATUS_MSG_417));
        }
    }

    @PutMapping("/parking/checkout")
    public ResponseEntity<ParkingCostDTO> checkOutVehicle(@RequestBody VehicleDTO vehicleDTO) {
        ParkingCostDTO parkingCostDTO = parkingService.checkOutVehicle(vehicleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(parkingCostDTO);
    }

    @GetMapping("/parking/details")
    public ResponseEntity<ParkingLotDTO> getParkingLotDetails(@RequestParam Integer lotId) {
        ParkingLotDTO parkingLotDTO = parkingService.getParkingLotDetails(lotId);
        return ResponseEntity.status(HttpStatus.OK).body(parkingLotDTO);
    }

    @GetMapping("/parking/vehicles")
    public ResponseEntity<List<VehicleDTO>> getAllVehiclesOfParkingLot(@RequestParam Integer lotId) {
        List<VehicleDTO> vehicleDTOList = parkingService.getAllVehiclesOfParkingLot(lotId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleDTOList);
    }


}
