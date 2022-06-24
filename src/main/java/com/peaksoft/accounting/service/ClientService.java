package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.*;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.repository.ClientRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.TagRepository;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.SellerAndClientRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final TagService tagService;
    private final SellerAndClientRequestValidator validator;
    private final InvoiceRepository invoiceRepository;

    public ClientResponse create(ClientEntity client, ClientRequest request) {
        validator.validate(client, request);
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
        client.getTags().forEach(t->t.getClients().remove(client));
        tagRepository.saveAll(client.getTags());
        client.getInvoices().forEach(i->i.setClient(null));
        invoiceRepository.saveAll(client.getInvoices());
        clientRepository.deleteById(id);
        return mapToResponse(client);
    }

    public ClientResponse findById(long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.CLIENT_NOT_FOUND);
        }
       ClientResponse response = mapToResponse(clientRepository.findById(id).get());
        response.setInvoices(invoiceRepository.getAllByClientAndStatus(id, InvoiceStatus.NOT_PAID));
        return response;
    }

    public PagedResponse<ClientResponse, Integer> getAllClients(String name, Long tagId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ClientEntity> pages = clientRepository.findAllByPagination(pageable,true);
        PagedResponse<ClientResponse, Integer> response = new PagedResponse<>();
        response.setResponses((map(search(name, tagId, pageable))));
        response.setTotalPage(pages.getTotalPages());
        return response;
    }
    public List<ClientResponse> findAll() {
        return map(clientRepository.findAll(true));
    }

    public ClientEntity mapToEntity(ClientRequest clientRequest) {
        ClientEntity client = new ClientEntity();
        client.setClientName(clientRequest.getClientName());
        client.setCompanyName(clientRequest.getCompanyName());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setCreated(LocalDateTime.now());
        client.setEmail(clientRequest.getEmail());
        client.setAddress(clientRequest.getAddress());
        for (Long tagsId : clientRequest.getTagsId()) {
            Optional<TagEntity> tags = tagRepository.findById(tagsId);
            if (tags.isEmpty()) {
                throw new ValidationException(ValidationExceptionType.TAG_NOT_FOUND);
            }
            client.addTags(tags.get());
        }
        return client;
    }

    public ClientEntity mapToUpdate(ClientEntity client, ClientRequest clientRequest) {
        client.setClientName(clientRequest.getClientName());
        client.setCompanyName(clientRequest.getCompanyName());
        client.setAddress(clientRequest.getAddress());
        client.setEmail(clientRequest.getEmail());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setIsIncome(true);
        for (Long tagsId : clientRequest.getTagsId()) {
            Optional<TagEntity> tags = tagRepository.findById(tagsId);
            client.removeTags(tags.get());
            client.addTags(tags.get());
        }
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
        client.setCompanyName(clientEntity.getCompanyName());
        client.setCreated(clientEntity.getCreated());
        client.setIsIncome(clientEntity.getIsIncome());
        client.setTags(tagService.map(clientEntity.getTags()));
        client.setAddress(clientEntity.getAddress());
        client.setEmail(clientEntity.getEmail());
        client.setPhoneNumber(clientEntity.getPhoneNumber());
        return client;
    }

    public List<ClientResponse> map(List<ClientEntity> clients) {
        List<ClientResponse> clientResponses = new ArrayList<>();
        for (ClientEntity client : clients) {
            clientResponses.add(mapToResponse(client));
        }
        return clientResponses;
    }

    public List<ClientEntity> search(String name, Long tagId, Pageable pageable) {
        String text = name == null ? "" : name;
        return clientRepository.search(text.toUpperCase(), tagId, pageable);
    }
}
