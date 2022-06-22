package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.BankAccountRequest;
import com.peaksoft.accounting.api.payload.BankAccountResponse;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.db.repository.BankAccountRepository;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.BankAccountRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepo;
    private final BankAccountRequestValidator accountValidator;

    public BankAccountResponse create(BankAccountEntity bankAccount, BankAccountRequest accountRequest) {
        accountValidator.validate(bankAccount, accountRequest);
        BankAccountEntity bankAccountEntity = mapToEntity(accountRequest);
        bankAccountRepo.save(bankAccountEntity);
        return mapToResponse(bankAccountEntity);
    }

    public BankAccountResponse update(long id, BankAccountRequest accountRequest) {
        Optional<BankAccountEntity> account = bankAccountRepo.findById(id);
        if (account.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        mapToUpdate(account.get(), accountRequest);
        return mapToResponse(bankAccountRepo.save(account.get()));
    }

    public BankAccountResponse deleteById(long id) {
        BankAccountEntity account = bankAccountRepo.findById(id).get();
        bankAccountRepo.deleteById(id);
        return mapToResponse(account);
    }

    public BankAccountResponse getById(long id) {
        Optional<BankAccountEntity> account = bankAccountRepo.findById(id);
        if (account.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.BANK_ACCOUNT_NOT_FOUND);
        }
        return mapToResponse(bankAccountRepo.findById(id).get());
    }

    public List<BankAccountResponse> getAllBankAccount(TypeOfPay typeOfPay) {
        return map(bankAccountRepo.findAll(typeOfPay));
    }


    public BankAccountEntity mapToEntity(BankAccountRequest request) {
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setDescription(request.getDescription());
        bankAccount.setBankAccountName(request.getBankAccountName());
        bankAccount.setTypeOfPay(request.getTypeOfPay());
        return bankAccount;
    }

    public BankAccountEntity mapToUpdate(BankAccountEntity bankAccount, BankAccountRequest request) {
        bankAccount.setBankAccountName(request.getBankAccountName());
        bankAccount.setDescription(request.getDescription());
        bankAccount.setTypeOfPay(request.getTypeOfPay());
        accountValidator.validate(bankAccount, request);
        return bankAccount;
    }

    public BankAccountResponse mapToResponse(BankAccountEntity bankAccount) {
        BankAccountResponse accountResponse = new BankAccountResponse();
        if (bankAccount == null) {
            return null;
        }
        if (bankAccount.getId() != null) {
            accountResponse.setId(String.valueOf(bankAccount.getId()));
        }
        accountResponse.setDescription(bankAccount.getDescription());
        accountResponse.setBankAccountName(bankAccount.getBankAccountName());
        accountResponse.setTypeOfPay(bankAccount.getTypeOfPay());
        return accountResponse;
    }

    public List<BankAccountResponse> map(List<BankAccountEntity> accountList) {
        List<BankAccountResponse> accountResponses = new ArrayList<>();
        for (BankAccountEntity bankAccount : accountList) {
            accountResponses.add(mapToResponse(bankAccount));
        }
        return accountResponses;
    }
}
