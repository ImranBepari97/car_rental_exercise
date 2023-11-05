package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.commands.CustomerCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testCreateCustomer() throws JsonProcessingException {
        CustomerCommand customerCommand = new CustomerCommand(
                "fn",
                "ln",
                LocalDate.MIN,
                "aaaaaa"
        );

        HttpEntity<String> request = createRequestFrom(customerCommand);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/customers"), request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetAllCustomers() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/customers"), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        long customerId = 1L;

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/customers/" + customerId), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}