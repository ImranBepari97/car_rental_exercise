package com.accelex.sample.exercise.controllers;

import com.accelex.sample.exercise.commands.RentalCommand;
import com.accelex.sample.exercise.commands.ReturnVehicleCommand;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.services.RentalCommandHandler;
import com.accelex.sample.exercise.services.RentalQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalCommandHandler rentalCommandHandler;
    private final RentalQueryHandler rentalQueryHandler;

    @PostMapping("/rent-vehicle")
    public ResponseEntity<?> rent(@RequestBody RentalCommand request) {
        rentalCommandHandler.rentVehicle(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/return-vehicle")
    public ResponseEntity<?> returnVehicle(@RequestBody ReturnVehicleCommand command) {
        rentalCommandHandler.returnVehicle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all-rented")
    public ResponseEntity<Page<Vehicle>> getAllRented(Pageable pageable) {
        return ResponseEntity.ok(rentalQueryHandler.getAllRented(pageable));
    }

}