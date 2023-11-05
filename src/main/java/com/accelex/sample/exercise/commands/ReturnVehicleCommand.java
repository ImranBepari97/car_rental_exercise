package com.accelex.sample.exercise.commands;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnVehicleCommand {
    @NotNull
    private Long customerId;

    @NotNull
    private String vehicleRegistration;

    @NotNull
    private boolean vehicleDamaged;
}
