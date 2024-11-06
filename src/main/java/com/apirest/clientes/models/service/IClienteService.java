package com.apirest.clientes.models.service;

import com.apirest.clientes.models.entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> findAll();
}
