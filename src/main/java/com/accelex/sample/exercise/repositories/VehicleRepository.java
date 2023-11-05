package com.accelex.sample.exercise.repositories;

import com.accelex.sample.exercise.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByRegistration(String registration);

    Vehicle findByRegistration(String registration);
}