package com.accelex.sample.exercise.commands;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VehicleCommand {
    @NotNull
    @NotEmpty
    private String brand;

    @NotNull
    @NotEmpty
    private String model;

    @NotNull
    private int year;

    @NotNull
    private String colour;

    @NotNull
    private String registration;
}
