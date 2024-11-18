package com.apirest.clients.models.service;

import com.apirest.clients.models.dao.IClientDAO;
import com.apirest.clients.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private IClientDAO clienteDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clienteDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clienteDAO.findById(id).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return clienteDAO.save(client);
    }

    @Override
    public void delete(Long id) {
        clienteDAO.deleteById(id);
    }
}
