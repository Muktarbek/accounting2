package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.BankAccountRequest;
import com.peaksoft.accounting.api.payload.BankAccountResponse;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/bankaccount")
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    public BankAccountResponse create(@RequestBody @Valid BankAccountRequest request, BankAccountEntity account){
       return bankAccountService.create(account,request);
    }
    @PutMapping("{id}")
    public BankAccountResponse update(@PathVariable long id, @RequestBody @Valid BankAccountRequest request){
        return bankAccountService.update(id,request);
    }
    @GetMapping("{id}")
    public BankAccountResponse getById(@PathVariable long id){
        return bankAccountService.getById(id);
    }
    @DeleteMapping("{id}")
    public BankAccountResponse delete(@PathVariable long id){
        return bankAccountService.deleteById(id);
    }
    @GetMapping
    public List<BankAccountResponse> getAllBankAccount(){
        return bankAccountService.getAllBankAccount();
    }
}
