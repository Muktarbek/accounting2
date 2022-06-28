package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.BankAccountRequest;
import com.peaksoft.accounting.api.payload.BankAccountResponse;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/bankaccount")
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@CrossOrigin
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    @Operation(summary = "Create bank account", description = "Creating a new Bank account")
    public BankAccountResponse create(@RequestBody @Valid BankAccountRequest request, BankAccountEntity account){
       return bankAccountService.create(account,request);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update bank account ", description = "Update a new bank account by \"id\" in application")
    public BankAccountResponse update(@PathVariable long id, @RequestBody BankAccountRequest request){
        return bankAccountService.update(id,request);
    }

    @GetMapping("{id}")
    @Operation(summary = "Getting bank account", description = "Getting an existing bank account by \"id\" in application")
    public BankAccountResponse getById(@PathVariable long id){
        return bankAccountService.getById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete bank account", description = "delete an existing bank account by \"id\" in application ")
    public BankAccountResponse delete(@PathVariable long id){
        return bankAccountService.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Get all bank account", description = "Getting all existing bank account ")
    public List<BankAccountResponse> getAllBankAccount(@RequestParam(required = false) TypeOfPay typeOfPay){
        return bankAccountService.getAllBankAccount(typeOfPay);
    }
}
