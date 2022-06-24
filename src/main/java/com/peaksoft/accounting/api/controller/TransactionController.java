package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.PaymentResponse;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/invoices")
@CrossOrigin
public class TransactionController {

    private final PaymentService paymentService;

    @GetMapping("/transaction")
    public PagedResponse<PaymentResponse, Integer> getTransaction(@RequestParam(required = false,defaultValue = "2000-01-01 01:01:01") String startDate,
                                                                  @RequestParam(required = false,defaultValue = "2030-01-01 01:01:01") String endDate,
                                                                  @RequestParam(required = false) Boolean  status,
                                                                  @RequestParam(required = false) TypeOfPay typeOfPay,
                                                                  @RequestParam(required = false) Long categoryId,
                                                                  @RequestParam int page, @RequestParam int size){
        return paymentService.transaction(startDate,endDate,status,typeOfPay,categoryId,size,page);
    }
}
