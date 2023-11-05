package com.accelex.sample.exercise.controllers;


import com.accelex.sample.exercise.commands.CustomerCommand;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.services.CustomerCommandHandler;
import com.accelex.sample.exercise.services.CustomerQueryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerCommandHandler customerCommandHandler;
    private final CustomerQueryHandler customerQueryHandler;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CustomerCommand costumer) {
        customerCommandHandler.create(costumer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(Pageable pageable) {
        return ResponseEntity.ok(customerQueryHandler.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable long id) {
        return ResponseEntity.ok(customerQueryHandler.getById(id));
    }
}