package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.commands.RentalCommand;
import com.accelex.sample.exercise.commands.ReturnVehicleCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.CUSTOMER_NOT_RENTING_VEHICLE_ERROR;
import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.VEHICLE_ALREADY_BOOKED_ERROR;
import static org.junit.jupiter.api.Assertions.*;

public class RentalControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testRentVehicle() {
        RentalCommand rentalCommand = new RentalCommand(
                3L, "MNO303", LocalDateTime.now()
        );

        HttpEntity<String> request = createRequestFrom(rentalCommand);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/rentals/rent-vehicle"), request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testReturnVehicle() {
        ReturnVehicleCommand returnVehicleCommand = new ReturnVehicleCommand(1L, "ABC123", false);

        HttpEntity<String> request = createRequestFrom(returnVehicleCommand);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/rentals/return-vehicle"), request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReturnVehicleNotRented() {
        ReturnVehicleCommand returnVehicleCommand = new ReturnVehicleCommand(4L, "GHI101", false);

        HttpEntity<String> request = createRequestFrom(returnVehicleCommand);

        Throwable exception = assertThrows(HttpClientErrorException.class, () -> {
        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/rentals/return-vehicle"), request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ((HttpClientErrorException) exception).getStatusCode());
        assertTrue(exception.getMessage().contains(CUSTOMER_NOT_RENTING_VEHICLE_ERROR));
    }

    @Test
    public void testRentVehicleAlreadyRented() {
        RentalCommand rentalCommand = new RentalCommand(
                3L, "DEF789", LocalDateTime.now()
        );

        HttpEntity<String> request = createRequestFrom(rentalCommand);
        Throwable exception = assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    createURLWithPort("/api/rentals/rent-vehicle"), request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ((HttpClientErrorException) exception).getStatusCode());
        assertTrue(exception.getMessage().contains(VEHICLE_ALREADY_BOOKED_ERROR));
    }

    @Test
    public void testGetAllRented() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/rentals/all-rented"), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}