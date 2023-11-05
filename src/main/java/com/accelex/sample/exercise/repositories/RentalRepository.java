package com.accelex.sample.exercise.repositories;

import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.RentalStatus;
import com.accelex.sample.exercise.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT rental FROM Rental rental WHERE rental.vehicle.registration = :vehicleRegistration ORDER BY rental.startDate")
    Optional<Rental> findMostRecentRentalByRegistration(@Param("vehicleRegistration") String vehicleRegistration);

    @Query("SELECT rental FROM Rental rental WHERE rental.vehicle.registration = :vehicleRegistration AND rental.customer.id = :customerId AND rental.returnDate IS NULL AND rental.status = :status")
    Optional<Rental> findRentedVehicleForCustomer(
            @Param("customerId") Long customerId,
            @Param("vehicleRegistration") String vehicleRegistration,
            @Param("status") RentalStatus status
    );

    @Query("SELECT rental.vehicle FROM Rental rental WHERE rental.returnDate IS NULL AND rental.status = :status")
    Page<Vehicle> findAllRentedVehicles(@Param("status") RentalStatus status, Pageable pageable);
}
