package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PaymentRequest;
import com.peaksoft.accounting.api.payload.PaymentResponse;
import com.peaksoft.accounting.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@RequestMapping("/api/myaccount/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/payment-for-product")
    @Operation(summary = "Create payment with product",description = "Creating a new payment")
    public PaymentResponse create(@RequestBody PaymentRequest request){
       return paymentService.createForProduct(request,true);
    }
    @PostMapping("/expense/payment-for-product")
    @Operation(summary = "Create payment with product",description = "Creating a new payment")
    public PaymentResponse createExpense(@RequestBody PaymentRequest request){
        return paymentService.createForProduct(request,false);
    }
    @PostMapping()
    @Operation(summary = "Create payment", description = "Creating a new payment")
    public PaymentResponse create(@RequestParam(required = false) long invoiceId, @RequestBody @Valid PaymentRequest request) {
        return paymentService.create(invoiceId, request);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update payment", description = "Update a new payment by \"id\" in application ")
    public PaymentResponse update(@PathVariable long id, PaymentRequest request) {
        return paymentService.update(id, request);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get payment", description = "Getting an existing payment by \"id\" in application ")
    public PaymentResponse findById(@PathVariable long id) {
        return paymentService.findById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete payment", description = "delete an existing payment by \"id\" in application ")
    public PaymentResponse delete(@PathVariable long id) {
        return paymentService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Getting all existing payments ")
    public List<PaymentResponse> getAllPayments() {
        return paymentService.gelAllPayments();
    }

}
