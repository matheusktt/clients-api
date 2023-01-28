package com.devsuperior.clientsapi.services;

import com.devsuperior.clientsapi.DTO.ClientDTO;
import com.devsuperior.clientsapi.entities.Client;
import com.devsuperior.clientsapi.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> list = repository.findAll(pageRequest);
        return list.map(listDTO -> new ClientDTO(listDTO));
    }
}