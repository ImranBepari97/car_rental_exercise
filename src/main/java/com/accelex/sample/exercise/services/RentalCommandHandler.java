package com.accelex.sample.exercise.services;

import com.accelex.sample.exercise.commands.RentalCommand;
import com.accelex.sample.exercise.commands.ReturnVehicleCommand;
import com.accelex.sample.exercise.exceptions.RentalException;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.RentalStatus;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import com.accelex.sample.exercise.repositories.RentalRepository;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.*;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class RentalCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalCommandHandler.class);

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public void rentVehicle(RentalCommand rentalCommand) {

        if (!vehicleRepository.existsByRegistration(rentalCommand.getVehicleRegistration()))
            throw new IllegalArgumentException(VEHICLE_NOT_EXIST_ERROR);

        RentalStatus status = getCurrentRentalStatus(rentalCommand.getVehicleRegistration());

        switch (status) {
            case OUT, PENDING -> throw new RentalException(VEHICLE_ALREADY_BOOKED_ERROR);
            case RETURNED_DAMAGED -> throw new RentalException(VEHICLE_DAMAGED_ERROR);
        }

        Rental rental = createRentalFrom(rentalCommand);

        Rental newRental = rentalRepository.save(rental);
        LOGGER.debug("Created new rental with ID: {}, customer ID: {}, vehicle ID: {}",
                newRental.getId(), newRental.getCustomer().getId(), newRental.getVehicle().getId());
    }

    public void returnVehicle(ReturnVehicleCommand returnVehicleCommand) {
        if (!vehicleRepository.existsByRegistration(returnVehicleCommand.getVehicleRegistration()))
            throw new IllegalArgumentException(VEHICLE_NOT_EXIST_ERROR);

        Rental rental = rentalRepository.findRentedVehicleForCustomer(
                returnVehicleCommand.getCustomerId(),
                returnVehicleCommand.getVehicleRegistration(),
                RentalStatus.OUT
        ).orElseThrow(() -> new RentalException(CUSTOMER_NOT_RENTING_VEHICLE_ERROR));


        if (returnVehicleCommand.isVehicleDamaged()) {
            rental.setStatus(RentalStatus.RETURNED_DAMAGED);
        } else {
            rental.setStatus(RentalStatus.RETURNED_OK);
        }

        Rental returnedRental = rentalRepository.save(rental);
        LOGGER.debug("Returned rental with ID: {}, customer ID: {}, vehicle ID: {}",
                returnedRental.getId(), returnedRental.getCustomer().getId(), returnedRental.getVehicle().getId());
    }


    private Rental createRentalFrom(RentalCommand rentalCommand) {
        Rental rental = new Rental();

        Customer customer = customerRepository.getById(rentalCommand.getCustomerId());
        rental.setCustomer(customer);

        Vehicle vehicle = vehicleRepository.findByRegistration(rentalCommand.getVehicleRegistration());
        rental.setVehicle(vehicle);

        rental.setStartDate(rentalCommand.getStartRentDate());

        boolean rentalHasStarted = rentalCommand.getStartRentDate().isBefore(LocalDateTime.now());

        if (rentalHasStarted) {
            rental.setStatus(RentalStatus.OUT);
        } else {
            rental.setStatus(RentalStatus.PENDING);
        }

        return rental;
    }


    private RentalStatus getCurrentRentalStatus(String vehicleRegistration) {
        Optional<Rental> mostRecentRental = rentalRepository.findMostRecentRentalByRegistration(vehicleRegistration);

        //If this car has never been rented before, we should default it to RETURNED_OK
        if (mostRecentRental.isEmpty())
            return RentalStatus.RETURNED_OK;

        return mostRecentRental.get().getStatus();
    }


}
