package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PaymentRequest;
import com.peaksoft.accounting.api.payload.PaymentResponse;
import com.peaksoft.accounting.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping()
    public PaymentResponse create(@RequestParam(required = false) long invoiceId, @RequestBody @Valid PaymentRequest request) {
        return paymentService.create(invoiceId, request);
    }

    @PutMapping("{id}")
    public PaymentResponse update(@PathVariable long id, PaymentRequest request) {
        return paymentService.update(id, request);
    }

    @GetMapping("{id}")
    public PaymentResponse findById(@PathVariable long id) {
        return paymentService.findById(id);
    }

    @DeleteMapping("{id}")
    public PaymentResponse delete(@PathVariable long id) {
        return paymentService.delete(id);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.gelAllPayments();
    }
}
