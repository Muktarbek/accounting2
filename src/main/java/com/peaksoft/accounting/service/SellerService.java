package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.ClientResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.SellerRequest;
import com.peaksoft.accounting.api.payload.SellerResponse;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.repository.ClientRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.SellerAndClientRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final ClientRepository sellerRepo;
    private final SellerAndClientRequestValidator validator;

    public SellerResponse create(ClientEntity seller, SellerRequest sellerRequest, CompanyEntity company) {
        validator.validate(seller, sellerRequest);
        ClientEntity sellers = mapToEntity(sellerRequest);
        sellers.setIsIncome(false);
        sellers.setCompany(company);
        sellerRepo.save(sellers);
        return mapToResponse(sellers);
    }

    public SellerResponse update(long id, SellerRequest sellerRequest,CompanyEntity company) {
        Optional<ClientEntity> sellers = sellerRepo.findById(id);
        if (sellers.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        mapToUpdate(sellers.get(), sellerRequest);
        sellers.get().setCompany(company);
        return mapToResponse(sellerRepo.save(sellers.get()));
    }

    public SellerResponse deleteById(long id) {
        ClientEntity seller = sellerRepo.findById(id).get();
        sellerRepo.delete(seller);
        return mapToResponse(seller);
    }

    public SellerResponse findById(long id) {
        Optional<ClientEntity> sellers = sellerRepo.findById(id);
        if (sellers.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.NOT_FOUND);
        }
        return mapToResponse(sellerRepo.findById(id).get());
    }

    public PagedResponse<SellerResponse, Integer> findAll(int page, int size,Long companyId) {
        List<SellerResponse> responses = new ArrayList<>();
        Page<ClientEntity> pagination = sellerRepo.findAllByPagination(PageRequest.of(page - 1, size),false,companyId);
        for (ClientEntity seller : pagination) {
            responses.add(mapToResponse(seller));
        }
        PagedResponse<SellerResponse, Integer> response = new PagedResponse<>();
        response.setResponses(responses);
        response.setTotalPage(pagination.getTotalPages());
        return response;
    }
    public List<SellerResponse> getAll(Long companyId) {
        return map(sellerRepo.findAll(false, companyId));
    }
    public List<SellerResponse> searchByName(String sellerName,Long companyId) {
        return map(sellerRepo.searchByName(sellerName,false,companyId));
    }
    public ClientEntity mapToEntity(SellerRequest sellerRequest) {
        ClientEntity client = new ClientEntity();
        client.setClientName(sellerRequest.getSellerName());
        client.setSellerSurname(sellerRequest.getSellerSurname());
        client.setCompanyName(sellerRequest.getCompanyName());
        client.setPhoneNumber(sellerRequest.getPhoneNumber());
        client.setCreated(LocalDateTime.now());
        client.setEmail(sellerRequest.getEmail());
        client.setAddress(sellerRequest.getAddress());
        return client;
    }

    public ClientEntity mapToUpdate(ClientEntity client, SellerRequest sellerRequest) {
        client.setClientName(sellerRequest.getSellerName());
        client.setCompanyName(sellerRequest.getCompanyName());
        client.setAddress(sellerRequest.getAddress());
        client.setEmail(sellerRequest.getEmail());
        client.setPhoneNumber(sellerRequest.getPhoneNumber());
        client.setSellerSurname(sellerRequest.getSellerSurname());
        client.setIsIncome(false);
        return client;
    }

    public SellerResponse mapToResponse(ClientEntity seller) {
        return SellerResponse.builder()
                .sellerId(seller.getClient_id())
                .sellerName(seller.getClientName())
                .sellerSurname(seller.getSellerSurname())
                .address(seller.getAddress())
                .companyName(seller.getCompanyName())
                .created(seller.getCreated())
                .email(seller.getEmail())
                .income(seller.getIsIncome())
                .phoneNumber(seller.getPhoneNumber())
                .build();
    }

    public List<SellerResponse> map(List<ClientEntity> sellers) {
        List<SellerResponse> sellerList = new ArrayList<>();
        for (ClientEntity seller : sellers) {
            sellerList.add(mapToResponse(seller));
        }
        return sellerList;
    }
}
