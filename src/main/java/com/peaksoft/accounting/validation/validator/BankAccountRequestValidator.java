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

    public void validate(BankAccountEntity bankAccount, BankAccountRequest accountRequest){

        if (bankAccount == null || accountRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (bankAccountRepository.findByBankAccountName(accountRequest.getBank_account_name()).isPresent()){
            throw new ValidationException(ValidationExceptionType.BANK_ACCOUNT_NAME_ALREADY_EXISTS);
        }
    }
}
