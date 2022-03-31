package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.ClientRequest;
import com.peaksoft.accounting.api.payload.ClientResponse;
import com.peaksoft.accounting.api.payload.ClientResponseView;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.repository.ClientRepository;
import com.peaksoft.accounting.db.repository.TagRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final TagRepository tagRepository;

    public ClientResponse create(ClientRequest request) {
        ClientEntity clientEntity = mapToEntity(request);
        clientRepository.save(clientEntity);
        return mapToResponse(clientEntity);
    }

    public ClientResponse update(ClientRequest clientRequest, long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        mapToUpdate(client.get(), clientRequest);
        return mapToResponse(clientRepository.save(client.get()));
    }

    public ClientResponse deleteById(long id) {
        ClientEntity client = clientRepository.findById(id).get();
        clientRepository.deleteById(id);
        return mapToResponse(client);
    }

    public ClientResponse findById(long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.CLIENT_NOT_FOUND);
        }
        return mapToResponse(clientRepository.findById(id).get());
    }

    public ClientResponseView getAllClients(String name, Integer page, Integer size){
        ClientResponseView responseView = new ClientResponseView();
        Pageable pageable = PageRequest.of(page, size);
        responseView.setClients(map(search(name, pageable)));
        return responseView;
    }

    public ClientEntity mapToEntity(ClientRequest clientRequest) {
        ClientEntity client = new ClientEntity();
        List<TagEntity> tagsList = new ArrayList<>();
        client.setClientName(clientRequest.getClientName());
        client.setCompanyName(clientRequest.getCompanyName());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setCreated(LocalDateTime.now());
        client.setEmail(clientRequest.getEmail());
        client.setAddress(clientRequest.getAddress());
        TagEntity tags = tagRepository.findById(clientRequest.getTags()).get();
        tagsList.add(tags);
        client.setTags(tagsList);
        return client;
    }

    public ClientEntity mapToUpdate(ClientEntity client, ClientRequest clientRequest) {
        List<TagEntity> tagsList = new ArrayList<>();
        client.setClientName(clientRequest.getClientName());
        client.setCompanyName(clientRequest.getCompanyName());
        client.setAddress(clientRequest.getAddress());
        client.setEmail(clientRequest.getEmail());
        client.setActive(client.isActive());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        TagEntity tags = tagRepository.findById(clientRequest.getTags()).get();
        tagsList.add(tags);
        client.setTags(tagsList);
        return client;
    }

    public ClientResponse mapToResponse(ClientEntity clientEntity) {
        ClientResponse client = new ClientResponse();

        if (clientEntity == null) {
            return null;
        }
        if (clientEntity.getClient_id() != null) {
            client.setClientId(String.valueOf(clientEntity.getClient_id()));
        }
        client.setClientName(clientEntity.getClientName());
        client.setActive(clientEntity.isActive());
        client.setCompanyName(clientEntity.getCompanyName());
        client.setCreated(clientEntity.getCreated());
        client.setTags(clientEntity.getTags());
        client.setAddress(clientEntity.getAddress());
        client.setEmail(clientEntity.getEmail());
        client.setPhoneNumber(clientEntity.getPhoneNumber());
        client.setActive(clientEntity.isActive());
        return client;
    }

    public List<ClientResponse> map(List<ClientEntity> orderEntities) {
        List<ClientResponse> clientResponses = new ArrayList<>();
        for (ClientEntity client : orderEntities) {
            clientResponses.add(mapToResponse(client));
        }
        return clientResponses;
    }

    public List<ClientEntity> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return clientRepository.search(text.toUpperCase(), pageable);
    }
}
