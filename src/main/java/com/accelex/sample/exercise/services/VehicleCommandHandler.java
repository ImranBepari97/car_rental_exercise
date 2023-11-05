package com.accelex.sample.exercise.services;


import com.accelex.sample.exercise.commands.VehicleCommand;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.VEHICLE_ALREADY_EXISTS_ERROR;

@Service
@AllArgsConstructor
public class VehicleCommandHandler {

    private final VehicleRepository vehicleRepository;

    public static Vehicle createVehicleFrom(VehicleCommand vehicleCommand) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(vehicleCommand.getBrand());
        vehicle.setModel(vehicleCommand.getModel());
        vehicle.setYear(vehicleCommand.getYear());
        vehicle.setColour(vehicleCommand.getColour());
        vehicle.setRegistration(vehicleCommand.getRegistration());

        return vehicle;
    }

    public void create(VehicleCommand vehicleCommand) {

        if (vehicleRepository.existsByRegistration(vehicleCommand.getRegistration()))
            throw new IllegalArgumentException(VEHICLE_ALREADY_EXISTS_ERROR);

        Vehicle vehicle = createVehicleFrom(vehicleCommand);

        vehicleRepository.save(vehicle);
    }
}