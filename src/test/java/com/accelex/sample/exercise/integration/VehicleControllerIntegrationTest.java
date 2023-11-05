package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.commands.VehicleCommand;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static com.accelex.sample.exercise.utils.ExceptionMessageConstants.VEHICLE_ALREADY_EXISTS_ERROR;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testCreateVehicle() {
        VehicleCommand vehicleCommand = new VehicleCommand(
                "brand1",
                "model1",
                2018,
                "blue",
                "TESTREG"
        );

        HttpEntity<String> request = createRequestFrom(vehicleCommand);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/vehicles/create"), request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateVehicleAlreadyExists() {
        VehicleCommand vehicleCommand = new VehicleCommand(
                "Vehicle3Brand",
                "Vehicle3Model",
                2019,
                "Color3",
                "DEF789"
        );

        HttpEntity<String> request = createRequestFrom(vehicleCommand);

        Throwable exception = assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    createURLWithPort("/api/vehicles/create"), request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ((HttpClientErrorException) exception).getStatusCode());
        assertTrue(exception.getMessage().contains(VEHICLE_ALREADY_EXISTS_ERROR));
    }
}