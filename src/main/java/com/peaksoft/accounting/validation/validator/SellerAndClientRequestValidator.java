package com.peaksoft.accounting.validation.validator;

import com.peaksoft.accounting.api.payload.ClientRequest;
import com.peaksoft.accounting.api.payload.SellerRequest;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.db.repository.ClientRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerAndClientRequestValidator {

    private final ClientRepository clientRepository;

    public void validate(ClientEntity client, ClientRequest clientRequest){

        if (client == null || clientRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (clientRepository.findByCompanyName(clientRequest.getCompanyName()).isPresent()){
            throw new ValidationException(ValidationExceptionType.THIS_NAME_ALREADY_EXISTS);
        }
        if (clientRepository.findByEmail(clientRequest.getEmail()).isPresent()) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        }
    }

    public void validate(ClientEntity client, SellerRequest sellerRequest){

        if (client == null || sellerRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (clientRepository.findByCompanyName(sellerRequest.getCompanyName()).isPresent()){
            throw new ValidationException(ValidationExceptionType.THIS_NAME_ALREADY_EXISTS);
        }
        if (clientRepository.findByEmail(sellerRequest.getEmail()).isPresent()) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        }
    }

}
