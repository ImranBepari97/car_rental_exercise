package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.ExerciseApplication;
import com.accelex.sample.exercise.commands.RentalCommand;
import com.accelex.sample.exercise.commands.ReturnVehicleCommand;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExerciseApplication.class)
public class RentalControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testRentVehicle() {
        RentalCommand rentalCommand = new RentalCommand(
                3L, "MNO303", LocalDateTime.now()
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/rentals/rent-vehicle"), rentalCommand, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testReturnVehicle() {
        ReturnVehicleCommand returnVehicleCommand = new ReturnVehicleCommand(1L, "ABC123", false);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/rentals/return-vehicle"), returnVehicleCommand, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllRented() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/rentals/all-rented"), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}