package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PaymentRequest;
import com.peaksoft.accounting.api.payload.PaymentResponse;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PaymentResponse create(@AuthenticationPrincipal UserEntity user, @RequestBody PaymentRequest request){
       return paymentService.createForProduct(request,true,user.getCompanyName());
    }

    @PostMapping("/expense/payment-for-product")
    @Operation(summary = "Create payment with product",description = "Creating a new payment")
    public PaymentResponse createExpense(@AuthenticationPrincipal UserEntity user,
                                         @RequestBody PaymentRequest request){
        return paymentService.createForProduct(request,false,user.getCompanyName());
    }

    @PostMapping()
    @Operation(summary = "Create payment", description = "Creating a new payment")
    public PaymentResponse create(@AuthenticationPrincipal UserEntity user,
                                  @RequestParam(required = false) long invoiceId,
                                  @RequestBody @Valid PaymentRequest request) {
        return paymentService.create(invoiceId, request,user.getCompanyName());
    }

    @PutMapping("{id}")
    @Operation(summary = "Update payment", description = "Update a new payment by \"id\" in application ")
    public PaymentResponse update(@AuthenticationPrincipal UserEntity user,
                                  @PathVariable long id,
                                  @RequestBody PaymentRequest request) {
        return paymentService.update(id, request,user.getCompanyName());
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
    public List<PaymentResponse> getAllPayments(@AuthenticationPrincipal UserEntity user) {
        return paymentService.gelAllPayments(user.getCompanyName().getCompany_id());
    }

}
