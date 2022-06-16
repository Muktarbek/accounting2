package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public InvoiceResponse create(@RequestBody @Valid InvoiceRequest invoiceRequest, InvoiceEntity invoice) {
        return invoiceService.create(invoiceRequest, invoice);
    }

    @GetMapping("/client")
    public PagedResponse<InvoiceResponse, Integer> getAllClientInvoices(@RequestParam int page,
                                                                        @RequestParam int size,
                                                                        @RequestParam(required = false) Long clientId,
                                                                        @RequestParam(value = "status", required = false, defaultValue = "") String status,
                                                                        @RequestParam(required = false, defaultValue = "2000-01-01 00:00:00") String startDate,
                                                                        @RequestParam(required = false, defaultValue = "2100-01-01 00:00:00") String endDate,
                                                                        @RequestParam(required = false) Long invoiceNumber) {
        return invoiceService.findAll(page, size, clientId, status, startDate, endDate, invoiceNumber, true);
    }

    @GetMapping("/seller")
    public PagedResponse<InvoiceResponse, Integer> getAllSellersInvoices(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) Long clientId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(required = false, defaultValue = "2000-01-01 00:00:00") String startDate,
            @RequestParam(required = false, defaultValue = "2100-01-01 00:00:00") String endDate,
            @RequestParam(required = false) Long invoiceNumber) {
        return invoiceService.findAll(page, size, clientId, status, startDate, endDate, invoiceNumber, false);
    }

    @PutMapping("{id}")
    public InvoiceResponse update(@PathVariable Long id, @RequestBody @Valid InvoiceRequest invoiceRequest) {
        return invoiceService.update(invoiceRequest, id);
    }

    @DeleteMapping("{id}")
    public InvoiceResponse deleteById(@PathVariable Long id) {
        return invoiceService.deleteById(id);
    }

    @GetMapping("{id}")
    public InvoiceResponse findById(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @PostMapping("send-by-tags")
    public InvoiceResponse sendByTags(@RequestBody InvoiceRequest invoiceRequest,
                                      @RequestParam Long tagId) {
        return invoiceService.sendByTags(invoiceRequest, tagId);
    }
    @GetMapping("/transaction")
    public PagedResponse<InvoiceResponse, Integer> getTransaction(@RequestParam(required = false,defaultValue = "2000-01-01 01:01:01") String startDate,
                                                                  @RequestParam(required = false,defaultValue = "2030-01-01 01:01:01") String endDate,
                                                                  @RequestParam(required = false) Boolean  status,
                                                                  @RequestParam(required = false) TypeOfPay typeOfPay,
                                                                  @RequestParam(required = false) Long categoryId,
                                                                  @RequestParam int page, @RequestParam int size){
        return invoiceService.transaction(startDate,endDate,status,typeOfPay,categoryId,size,page);
    }
}
