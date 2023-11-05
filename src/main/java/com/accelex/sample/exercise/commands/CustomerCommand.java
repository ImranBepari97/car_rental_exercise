package com.accelex.sample.exercise.commands;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerCommand {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @Past
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String driverLicenseNumber;
}
