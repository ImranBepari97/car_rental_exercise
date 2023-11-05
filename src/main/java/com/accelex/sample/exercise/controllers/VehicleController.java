package com.accelex.sample.exercise.controllers;

import com.accelex.sample.exercise.commands.VehicleCommand;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.services.VehicleCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleCommandHandler vehicleCommandHandler;

    @PostMapping("/create")
    public ResponseEntity<Vehicle> create(@RequestBody VehicleCommand request) {
        vehicleCommandHandler.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}