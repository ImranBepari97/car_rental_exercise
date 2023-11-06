package com.accelex.sample.exercise.unit;

import com.accelex.sample.exercise.commands.VehicleCommand;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import com.accelex.sample.exercise.services.VehicleCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VehicleCommandHandlerUnitTest {

    @Mock
    private VehicleRepository vehicleRepository;

    private VehicleCommandHandler vehicleCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleCommandHandler = new VehicleCommandHandler(vehicleRepository);
    }

    @Test
    public void testCreateVehicle() {
        VehicleCommand vehicleCommand = new VehicleCommand(
                "Toyota",
                "Camry",
                2023,
                "Black",
                "ABCD1234"
        );

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setYear(2023);
        vehicle.setColour("Black");
        vehicle.setRegistration("ABCD1234");

        when(vehicleRepository.existsByRegistration(vehicleCommand.getRegistration())).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleCommandHandler.create(vehicleCommand);

        verify(vehicleRepository, times(1)).existsByRegistration(vehicleCommand.getRegistration());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }


    @Test
    public void testCreateVehicleAlreadyExists() {
        VehicleCommand vehicleCommand = new VehicleCommand("VW", "Polo", 2022, "Red", "ABC123");

        when(vehicleRepository.existsByRegistration(vehicleCommand.getRegistration())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> vehicleCommandHandler.create(vehicleCommand));

        verify(vehicleRepository, times(1)).existsByRegistration(vehicleCommand.getRegistration());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    public void testCreateVehicleFromCommand() {
        VehicleCommand vehicleCommand = new VehicleCommand("Toyota", "Camry", 2022, "Red", "ABC123");
        Vehicle vehicle = VehicleCommandHandler.createVehicleFrom(vehicleCommand);

        assertEquals(vehicle.getBrand(), "Toyota");
        assertEquals(vehicle.getModel(), "Camry");
        assertEquals(vehicle.getYear(), 2022);
        assertEquals(vehicle.getColour(), "Red");
        assertEquals(vehicle.getRegistration(), "ABC123");
    }
}