package com.smartpark.exam.config;

import com.smartpark.exam.entity.ParkingLot;
import com.smartpark.exam.entity.Vehicle;
import com.smartpark.exam.repository.ParkingLotRepository;
import com.smartpark.exam.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerConfig {

    private final VehicleRepository vehicleRepository;

    @Scheduled(fixedRate = 1000)
    @Transactional
    @Modifying
    public void scheduleRemovalOFVehicleParked() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();

        vehicleList.stream().forEach( v -> {
            long minutes = ChronoUnit.MINUTES.between(v.getCheckIn(), LocalDateTime.now());
            if(minutes >= 15) {
                v.getParkingLot().setOccupiedSpaces(v.getParkingLot().getOccupiedSpaces() - 1);
                v.getParkingLot().getVehicle().remove(v);
                v.setParkingLot(null);
                vehicleRepository.save(v);
                vehicleRepository.deleteByLicensePlate(v.getLicensePlate());
            }
        });

    }
}
