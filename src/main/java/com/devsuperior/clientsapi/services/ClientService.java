package com.devsuperior.clientsapi.services;

import com.devsuperior.clientsapi.DTO.ClientDTO;
import com.devsuperior.clientsapi.entities.Client;
import com.devsuperior.clientsapi.repositories.ClientRepository;
import com.devsuperior.clientsapi.services.exceptions.DatabaseException;
import com.devsuperior.clientsapi.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getOne(id);

            entity.setName(dto.getName());
            entity.setCpf(dto.getCpf());
            entity.setIncome(dto.getIncome());
            entity.setBirthDate(dto.getBirthDate());
            entity.setChildren(dto.getChildren());

            entity = repository.save(entity);

            return new ClientDTO(entity);
        }catch (EntityNotFoundException error){
            throw  new ResourceNotFoundException("Client not found: " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException error) {
            throw new ResourceNotFoundException("Client not found: " + id);
        } catch (DataIntegrityViolationException error) {
            throw new DatabaseException("Integrity violation");
        }
    }
}