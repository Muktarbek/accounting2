package com.peaksoft.accounting.api.controller;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.TPRequest;
import com.peaksoft.accounting.api.payload.TPResponse;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.service.TPService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/transaction")
public class TransactionController {

    private final TPService tpService;

    @PostMapping
    @Operation(summary = "Create payment", description = "Creating a new payment")
    public TPResponse create(@RequestBody TPRequest request) {
        return tpService.create(request);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update payment", description = "Update a new payment by \"id\" in application ")
    public TPResponse update(@PathVariable long id, @RequestBody TPRequest request) {
        return tpService.update(id, request);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get payment", description = "Getting an existing payment by \"id\" in application ")
    public TPResponse findById(@PathVariable long id) {
        return tpService.findById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete payment", description = "delete an existing payment by \"id\" in application ")
    public TPResponse delete(@PathVariable long id) {
        return tpService.delete(id);
    }

    @GetMapping("/payments")
    @Operation(summary = "Get all payments", description = "Getting all existing payments ")
    public List<TPResponse> getAllPayments() {
        return tpService.gelAllPayments();
    }

    @GetMapping
    public PagedResponse<TPResponse, Integer> getAllTransaction(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) TypeOfPay typeOfPay,
            @RequestParam(required = false) Long category) {
        return tpService.findAllTransaction(page, size,typeOfPay, category);
    }

}
