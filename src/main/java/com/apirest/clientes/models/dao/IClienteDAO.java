package com.apirest.clientes.models.dao;

import com.apirest.clientes.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface IClienteDAO extends CrudRepository<Cliente, Long> {
    Cliente findById(long id);

    Cliente findByName(String name);

    Cliente findByLastname(String lastName);

    List<Cliente> findAllByOrderByIdDesc();

    Cliente findByEmail(String email);

    Cliente findByCreatedAt(Date createdAt);
}
