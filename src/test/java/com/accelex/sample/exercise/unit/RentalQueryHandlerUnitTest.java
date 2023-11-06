package com.accelex.sample.exercise.unit;

import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repositories.RentalRepository;
import com.accelex.sample.exercise.repositories.VehicleRepository;
import com.accelex.sample.exercise.services.RentalQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RentalQueryHandlerUnitTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    private RentalQueryHandler rentalQueryHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalQueryHandler = new RentalQueryHandler(rentalRepository, vehicleRepository);
    }

    @Test
    public void testGetAllRented() {
        Pageable pageable = Pageable.unpaged();
        Page<Vehicle> mockPage = new PageImpl<>(Collections.singletonList(new Vehicle()), pageable, 1);
        when(vehicleRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Vehicle> result = rentalQueryHandler.getAllRented(pageable);

        assertEquals(1, result.getContent().size());
    }
}