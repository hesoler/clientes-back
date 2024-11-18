package com.apirest.clients.models.service;

import com.apirest.clients.models.entity.Client;

import java.util.List;

public interface IClientService {
    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void delete(Long id);
}
