package com.accelex.sample.exercise.services;

import com.accelex.sample.exercise.commands.CustomerCommand;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerCommandHandler {

    @Autowired
    private final CustomerRepository customerRepository;

    public static Customer createCustomerFrom(CustomerCommand customerCommand) {
        Customer customer = new Customer();

        customer.setFirstName(customerCommand.getFirstName());
        customer.setLastName(customerCommand.getLastName());
        customer.setBirthDate(customerCommand.getBirthDate());
        customer.setDriverLicenseNumber(customerCommand.getDriverLicenseNumber());

        return customer;
    }

    public void create(CustomerCommand customerCommand) {
        if (customerRepository.existsByDriverLicenseNumber(customerCommand.getDriverLicenseNumber()))
            throw new IllegalArgumentException("Customer with entered license already exists!");

        Customer customer = createCustomerFrom(customerCommand);

        customerRepository.save(customer);
    }
}
