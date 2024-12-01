package com.apirest.clients.controllers;

import com.apirest.clients.models.entity.Client;
import com.apirest.clients.models.service.IClientService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {

    private final IClientService clientService;

    @Autowired
    public ClientRestController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clientes")
    public List<Client> index() {
        return clientService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Client client;
        Map<String, Object> response = new HashMap<>();

        try {
            client = clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error executing query: " + e.getMessage());
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null) {
            response.put("message", "Client with ID: " + id + " not found in database");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Client client) {

        Client newClient;
        Map<String, Object> response = new HashMap<>();
        try {
            newClient = clientService.save(client);
        } catch (DataAccessException e) {
            response.put("message", "Error executing query: " + e.getMessage());
            response.put("description", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ConstraintViolationException e) {
            response.put("message", "Invalid fields or existing email");
            response.put("description", e.getConstraintViolations().toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "Client has been created successfully!");
        response.put("client", newClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Client client) {

        Client findById = clientService.findById(id);
        Client updatedClient;
        Map<String, Object> response = new HashMap<>();

        if (client == null) {
            response.put("message", "Error: Can not update. Client with ID: " + id + " not found in database");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            findById.setName(client.getName());
            findById.setLastName(client.getLastName());
            findById.setEmail(client.getEmail());
            findById.setCreatedAt(client.getCreatedAt());

            updatedClient = clientService.save(findById);

        } catch (TransactionSystemException e) {
            response.put("message", "Error executing query: " + e.getMessage());
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (NullPointerException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("message", "Client has been updated successfully!");
        response.put("client", updatedClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Client findById = clientService.findById(id);
        if (findById == null) {
            response.put("message", "Error deleting client: Client with ID: " + id + " was not found in database");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clientService.delete(id);
        } catch (Exception e) {
            response.put("message", "Error deleting client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Client has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
