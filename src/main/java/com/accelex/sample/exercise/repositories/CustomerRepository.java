package com.accelex.sample.exercise.repositories;

import com.accelex.sample.exercise.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByDriverLicenseNumber(String number);
}
