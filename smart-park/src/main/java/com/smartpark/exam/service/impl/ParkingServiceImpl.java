package com.smartpark.exam.service.impl;

import com.smartpark.exam.dto.ParkingCostDTO;
import com.smartpark.exam.dto.ParkingLotDTO;
import com.smartpark.exam.dto.VehicleDTO;
import com.smartpark.exam.entity.ParkingLot;
import com.smartpark.exam.entity.Vehicle;
import com.smartpark.exam.exception.ResourceNotFoundException;
import com.smartpark.exam.exception.VehicleAlreadyExistsException;
import com.smartpark.exam.mapper.ParkingLotMapper;
import com.smartpark.exam.mapper.VehicleMapper;
import com.smartpark.exam.repository.ParkingLotRepository;
import com.smartpark.exam.repository.VehicleRepository;
import com.smartpark.exam.service.IParkingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParkingServiceImpl implements IParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void registerParkingLot(ParkingLotDTO parkingLotDTO) {
        this.parkingLotRepository.save(ParkingLotMapper.mapToParkingLot(new ParkingLot(), parkingLotDTO, new HashSet<>()));
    }

    @Override
    public void registerVehicle(VehicleDTO vehicleDTO) {
        Optional<Vehicle> vehicleOpt = this.vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate());
        if(vehicleOpt.isEmpty()) {
            this.vehicleRepository.save(VehicleMapper.mapToVehicle(new Vehicle(), vehicleDTO, null));
        } else {
            throw new VehicleAlreadyExistsException("Vehicle with license plate of " + vehicleDTO.getLicensePlate() + " already exists");
        }
    }

    @Override
    public boolean checkInVehicle(VehicleDTO vehicleDTO) {
        Optional<Vehicle> vehicleOpt = this.vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate());
        if(vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            Optional<ParkingLot> parkingLotOpt = this.parkingLotRepository.findById(vehicleDTO.getLotId());
            if(parkingLotOpt.isPresent() && isOpenForCheckInVehicle(parkingLotOpt.get().getCapacity(), parkingLotOpt.get().getOccupiedSpaces())) {
                ParkingLot parkingLot = parkingLotOpt.get();
                parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);
                this.parkingLotRepository.save(parkingLot);
                vehicle.setParkingLot(parkingLot);
                vehicle.setCheckIn(LocalDateTime.now());
                this.vehicleRepository.save(vehicle);
            }else{
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ParkingCostDTO checkOutVehicle(VehicleDTO vehicleDTO) {
        ParkingCostDTO parkingCostDTO = new ParkingCostDTO();
        Optional<Vehicle> vehicleOpt = this.vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate());
        if(vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            Optional<ParkingLot> parkingLotOpt = this.parkingLotRepository.findById(vehicleDTO.getLotId());
            if (parkingLotOpt.isPresent()) {
                ParkingLot parkingLot = parkingLotOpt.get();
                parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
                this.parkingLotRepository.save(parkingLot);
                vehicle.setParkingLot(null);
                vehicle.setCheckOut(LocalDateTime.now());
                this.vehicleRepository.save(vehicle);
                parkingCostDTO.setLicensePlate(vehicle.getLicensePlate());
                parkingCostDTO.setLotId(parkingLot.getLotId());
                parkingCostDTO.setCheckInDateTime(vehicle.getCheckIn());
                parkingCostDTO.setCheckOutDateTime(vehicle.getCheckOut());
                parkingCostDTO.setTotalCost(getTotalCostOfParking(vehicle.getCheckIn(), vehicle.getCheckOut(), parkingLot.getCostPerMinute()));
                return parkingCostDTO;
            } else {
                throw new ResourceNotFoundException("ParkingLot", "lotId", String.valueOf(vehicleDTO.getLotId()));
            }
        } else {
            throw new ResourceNotFoundException("Vehicle", "licensePlate", String.valueOf(vehicleDTO.getLicensePlate()));
        }
    }

    @Override
    public ParkingLotDTO getParkingLotDetails(Integer lotId) {
        Optional<ParkingLot> parkingLotOpt = this.parkingLotRepository.findById(lotId);
        if(parkingLotOpt.isPresent()) {
            return ParkingLotMapper.mapToParkingLotDTO(new ParkingLotDTO(), parkingLotOpt.get());
        } else {
            throw new ResourceNotFoundException("ParkingLot", "lotId", String.valueOf(lotId));
        }
    }

    @Override
    public List<VehicleDTO> getAllVehiclesOfParkingLot(Integer lotId) {
        Optional<ParkingLot> parkingLotOpt = this.parkingLotRepository.findById(lotId);
        if(parkingLotOpt.isPresent()) {
            return parkingLotOpt.get().getVehicle().stream().map(v -> VehicleMapper.mapToVehiclDTO(new VehicleDTO(), v, lotId)).collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("ParkingLot", "lotId", String.valueOf(lotId));
        }
    }

    private boolean isOpenForCheckInVehicle(Integer capacity, Integer occupiedSpaces) {
        if(capacity != null && occupiedSpaces != null) {
            if ((capacity.intValue() - occupiedSpaces.intValue()) > 0) {
                return true;
            }
        }
        return false;
    }

    private Double getTotalCostOfParking(LocalDateTime from, LocalDateTime to, Double costPerMins) {
        long minutes = ChronoUnit.MINUTES.between(from, to);
        return costPerMins * Long.valueOf(minutes).doubleValue();
    }
}
