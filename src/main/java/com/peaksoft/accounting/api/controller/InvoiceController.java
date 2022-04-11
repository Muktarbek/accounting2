package com.peaksoft.accounting.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.Response;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
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
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public InvoiceResponse create(@RequestBody @Valid InvoiceRequest invoiceRequest, InvoiceEntity invoice) {
        return invoiceService.create(invoiceRequest,invoice);
    }

    @GetMapping
    public Response<InvoiceResponse,Integer> getAllInvoices(@RequestParam int page,
                                                            @RequestParam int size,
                                                            @RequestParam(required = false) Long clientId,
                                                            @RequestParam(required = false) String status,
                                                            @RequestParam(required = false,defaultValue = "2000-01-01 00:00:00") String startDate,
                                                            @RequestParam(required = false,defaultValue = "2100-01-01 00:00:00") String endDate,
                                                            @RequestParam(required = false) Long  invoiceNumber
                                                            ){
        return invoiceService.findAll(page,size,clientId,status,startDate,endDate,invoiceNumber);
    }

    @PutMapping("{id}")
    public InvoiceResponse update(@PathVariable Long id, @RequestBody @Valid InvoiceRequest invoiceRequest){
        System.out.println(id);
        return invoiceService.update(invoiceRequest,id);
    }

    @DeleteMapping("{id}")
    public InvoiceResponse deleteById(@PathVariable Long id){
        return invoiceService.deleteById(id);
    }

    @GetMapping("{id}")
    public InvoiceResponse findById(@PathVariable Long id){
        return invoiceService.getById(id);
    }
    @PostMapping("send-by-tags")
    public InvoiceResponse sendByTags(@RequestBody InvoiceRequest invoiceRequest,
                                      @RequestParam  Long tagId){
        return invoiceService.sendByTags(invoiceRequest,tagId);
    }
}
