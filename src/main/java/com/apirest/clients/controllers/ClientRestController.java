package com.apirest.clients.controllers;

import com.apirest.clients.models.entity.Client;
import com.apirest.clients.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clientes")
    public List<Client> index() {
        return clientService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public Client show(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        client.setCreatedAt(new Date());
        return clientService.save(client);
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@PathVariable Long id, @RequestBody Client client) {

        Client findById = clientService.findById(id);
        findById.setName(client.getName());
        findById.setLastName(client.getLastName());
        findById.setEmail(client.getEmail());

        return clientService.save(findById);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
