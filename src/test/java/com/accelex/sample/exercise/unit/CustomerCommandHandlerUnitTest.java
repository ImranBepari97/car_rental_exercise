package com.accelex.sample.exercise.unit;

import com.accelex.sample.exercise.commands.CustomerCommand;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import com.accelex.sample.exercise.services.CustomerCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerCommandHandlerUnitTest {
    private CustomerCommandHandler customerCommandHandler;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerCommandHandler = new CustomerCommandHandler(customerRepository);
    }

    @Test
    public void testCreateCustomer() {
        CustomerCommand customerCommand = new CustomerCommand("John", "Doe", LocalDate.of(1990, 5, 12), "ABCD1234");
        customerCommandHandler.create(customerCommand);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testCreateCustomerCustomerExists() {
        when(customerRepository.existsByDriverLicenseNumber(anyString())).thenReturn(true);
        CustomerCommand customerCommand = new CustomerCommand("John", "Doe", LocalDate.of(1990, 5, 12), "ABCD1234");

        assertThrows(IllegalArgumentException.class, () -> customerCommandHandler.create(customerCommand));
    }
}
