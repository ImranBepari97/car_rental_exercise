package com.accelex.sample.exercise.services;

import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.RentalRepository;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalQueryHandler {

    @Autowired
    private final RentalRepository rentalRepository;
    @Autowired
    private final VehicleRepository vehicleRepository;

    public Page<Vehicle> getAllRented(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }
}
