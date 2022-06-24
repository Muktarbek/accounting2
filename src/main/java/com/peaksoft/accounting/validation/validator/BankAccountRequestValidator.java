package com.peaksoft.accounting.validation.validator;

import com.peaksoft.accounting.api.payload.BankAccountRequest;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.db.repository.BankAccountRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankAccountRequestValidator {

    private final BankAccountRepository bankAccountRepository;

    public void validate(BankAccountEntity bankAccount, BankAccountRequest accountRequest,Long id){

        if (bankAccount == null || accountRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (bankAccountRepository.findByBankAccountName(accountRequest.getBankAccountName()).isPresent()
                && bankAccountRepository.findByBankAccountName(accountRequest.getBankAccountName()).get().getId()!=id){
            throw new ValidationException(ValidationExceptionType.THIS_NAME_ALREADY_EXISTS);
        }
    }
}
