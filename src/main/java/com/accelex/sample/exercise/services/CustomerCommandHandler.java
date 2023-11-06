package com.accelex.sample.exercise.services;

import com.accelex.sample.exercise.commands.CustomerCommand;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.CUSTOMER_ALREADY_EXISTS_ERROR;

@Service
@AllArgsConstructor
public class CustomerCommandHandler {

    @Autowired
    private final CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCommandHandler.class);

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
            throw new IllegalArgumentException(CUSTOMER_ALREADY_EXISTS_ERROR);

        Customer customer = createCustomerFrom(customerCommand);

        Customer newCustomer = customerRepository.save(customer);
        LOGGER.debug("Created new customer with ID: {}, name: {} {}",
                newCustomer.getId(), newCustomer.getFirstName(), newCustomer.getLastName());
    }
}
