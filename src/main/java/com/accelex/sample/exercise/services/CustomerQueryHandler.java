package com.accelex.sample.exercise.services;

import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerQueryHandler {

    @Autowired
    private final CustomerRepository customerRepository;

    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer getById(long id) {
        return customerRepository.findById(id).orElseThrow();
    }
}
