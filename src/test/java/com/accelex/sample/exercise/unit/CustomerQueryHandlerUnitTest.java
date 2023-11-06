package com.accelex.sample.exercise.unit;


import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repositories.CustomerRepository;
import com.accelex.sample.exercise.services.CustomerQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerQueryHandlerUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerQueryHandler customerQueryHandler;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerQueryHandler = new CustomerQueryHandler(customerRepository);
    }

    @Test
    public void testGetAllCustomers() {
        Page<Customer> mockCustomerPage = mock(Page.class);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(mockCustomerPage);

        Page<Customer> result = customerQueryHandler.getAll(Pageable.unpaged());

        assertEquals(mockCustomerPage, result);
        verify(customerRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetCustomerById() {
        long customerId = 1L;
        Customer mockCustomer = new Customer(1L, "John", "Doe", LocalDate.MIN, "aaaaaaa");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Customer result = customerQueryHandler.getById(customerId);

        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }
}