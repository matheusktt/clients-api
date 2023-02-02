package com.devsuperior.clientsapi.services;

import com.devsuperior.clientsapi.DTO.ClientDTO;
import com.devsuperior.clientsapi.entities.Client;
import com.devsuperior.clientsapi.repositories.ClientRepository;
import com.devsuperior.clientsapi.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {

        Page<Client> list = repository.findAll(pageRequest);

        return list.map(listDTO -> new ClientDTO(listDTO));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {

        Optional<Client> client = repository.findById(id);

        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert (ClientDTO dto) {

        Client entity = new Client();

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

        entity = repository.save(entity);

        return new ClientDTO(entity);
    }
}