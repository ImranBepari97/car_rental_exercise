package com.accelex.sample.exercise.commands;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RentalCommand {
    @NotNull
    private Long customerId;

    @NotNull
    private String vehicleRegistration;

    @NotNull
    private LocalDateTime startRentDate;
}
