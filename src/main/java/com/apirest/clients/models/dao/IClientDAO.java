package com.apirest.clients.models.dao;

import com.apirest.clients.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface IClientDAO extends CrudRepository<Client, Long> {
    Client findById(long id);

    Client findByName(String name);

    Client findByLastName(String lastName);

    List<Client> findAllByOrderByIdDesc();

    Client findByEmail(String email);

    Client findByCreatedAt(Date createdAt);
}
