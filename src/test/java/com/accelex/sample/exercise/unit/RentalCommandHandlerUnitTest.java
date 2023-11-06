package com.accelex.sample.exercise.unit;


import com.accelex.sample.exercise.commands.RentalCommand;
import com.accelex.sample.exercise.commands.ReturnVehicleCommand;
import com.accelex.sample.exercise.exceptions.RentalException;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.RentalStatus;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import com.accelex.sample.exercise.repositories.RentalRepository;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import com.accelex.sample.exercise.services.RentalCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RentalCommandHandlerUnitTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    private RentalCommandHandler rentalCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalCommandHandler = new RentalCommandHandler(rentalRepository, customerRepository, vehicleRepository);
    }

    @Test
    public void testRentVehicle() {
        RentalCommand rentalCommand = new RentalCommand(1L, "ABC123", LocalDateTime.now());

        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setRegistration("ABC123");

        when(vehicleRepository.existsByRegistration("ABC123")).thenReturn(true);
        when(rentalRepository.findMostRecentRentalByRegistration("ABC123")).thenReturn(Optional.empty());
        when(vehicleRepository.findByRegistration("ABC123")).thenReturn(mockVehicle);
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        rentalCommandHandler.rentVehicle(rentalCommand);

        verify(vehicleRepository, times(1)).existsByRegistration("ABC123");
        verify(rentalRepository, times(1)).findMostRecentRentalByRegistration("ABC123");
        verify(vehicleRepository, times(1)).findByRegistration("ABC123");
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    public void testRentVehicleAlreadyBooked() {
        RentalCommand rentalCommand = new RentalCommand(1L, "ABC123", LocalDateTime.now());

        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setRegistration("ABC123");

        Rental mockRental = new Rental();
        mockRental.setStatus(RentalStatus.OUT);

        when(vehicleRepository.existsByRegistration("ABC123")).thenReturn(true);
        when(rentalRepository.findMostRecentRentalByRegistration("ABC123")).thenReturn(Optional.of(mockRental));
        when(vehicleRepository.findByRegistration("ABC123")).thenReturn(mockVehicle);

        try {
            rentalCommandHandler.rentVehicle(rentalCommand);
        } catch (RentalException e) {
            assertEquals("Vehicle is already booked!", e.getMessage());
        }
    }

    @Test
    public void testReturnVehicle() {
        ReturnVehicleCommand returnVehicleCommand = new ReturnVehicleCommand(1L, "ABC123", true);

        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setRegistration("ABC123");

        Rental mockRental = new Rental();
        mockRental.setStatus(RentalStatus.OUT);

        when(vehicleRepository.existsByRegistration("ABC123")).thenReturn(true);
        when(rentalRepository.findRentedVehicleForCustomer(1L, "ABC123", RentalStatus.OUT)).thenReturn(Optional.of(mockRental));
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        rentalCommandHandler.returnVehicle(returnVehicleCommand);

        verify(vehicleRepository, times(1)).existsByRegistration("ABC123");
        verify(rentalRepository, times(1)).findRentedVehicleForCustomer(1L, "ABC123", RentalStatus.OUT);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    public void testReturnVehicleDamaged() {
        ReturnVehicleCommand returnVehicleCommand = new ReturnVehicleCommand(1L, "ABC123", true);

        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setRegistration("ABC123");

        Rental mockRental = new Rental();
        mockRental.setStatus(RentalStatus.OUT);

        when(vehicleRepository.existsByRegistration("ABC123")).thenReturn(true);
        when(rentalRepository.findRentedVehicleForCustomer(1L, "ABC123", RentalStatus.OUT)).thenReturn(Optional.of(mockRental));
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        rentalCommandHandler.returnVehicle(returnVehicleCommand);

        verify(vehicleRepository, times(1)).existsByRegistration("ABC123");
        verify(rentalRepository, times(1)).findRentedVehicleForCustomer(1L, "ABC123", RentalStatus.OUT);
        verify(rentalRepository, times(1)).save(any(Rental.class));
        assertEquals(RentalStatus.RETURNED_DAMAGED, mockRental.getStatus());
    }
}