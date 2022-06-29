package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ClientInvoicesResponse;
import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@RequestMapping("/api/myaccount/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public InvoiceResponse create(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid InvoiceRequest invoiceRequest, InvoiceEntity invoice) {
        return invoiceService.create(invoiceRequest, invoice,true,user.getCompanyName());
    }
    @PostMapping("/expense")
    public InvoiceResponse createExpense(@AuthenticationPrincipal UserEntity user,@RequestBody @Valid InvoiceRequest invoiceRequest, InvoiceEntity invoice) {
        return invoiceService.create(invoiceRequest, invoice,false,user.getCompanyName());
    }

    @GetMapping("/client")
    public PagedResponse<InvoiceResponse, Integer> getAllClientInvoices(@AuthenticationPrincipal UserEntity user,
                                                                        @RequestParam int page,
                                                                        @RequestParam int size,
                                                                        @RequestParam(required = false) Long clientId,
                                                                        @RequestParam(value = "status", required = false, defaultValue = "") String status,
                                                                        @RequestParam(required = false, defaultValue = "2000-01-01 00:00:00") String startDate,
                                                                        @RequestParam(required = false, defaultValue = "2100-01-01 00:00:00") String endDate,
                                                                        @RequestParam(required = false) Long invoiceNumber) {
        return invoiceService.findAll(page, size, clientId, status, startDate, endDate, invoiceNumber, true,user.getCompanyName().getCompany_id());
    }

    @GetMapping("/seller")
    public PagedResponse<InvoiceResponse, Integer> getAllSellersInvoices(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) Long clientId,
            @RequestParam(value = "status", required = false,defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "2000-01-01 00:00:00") String startDate,
            @RequestParam(required = false, defaultValue = "2100-01-01 00:00:00") String endDate,
            @RequestParam(required = false) Long invoiceNumber) {
        return invoiceService.findAll(page, size, clientId, status, startDate, endDate, invoiceNumber, false,user.getCompanyName().getCompany_id());
    }

    @PutMapping("{id}")
    public InvoiceResponse update(@AuthenticationPrincipal UserEntity user,
                                  @PathVariable Long id,
                                  @RequestBody @Valid InvoiceRequest invoiceRequest) {
        return invoiceService.update(invoiceRequest, id,user.getCompanyName());
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
    public InvoiceResponse sendByTags(@AuthenticationPrincipal UserEntity user,@RequestBody InvoiceRequest invoiceRequest,
                                      @RequestParam Long tagId) {
        return invoiceService.sendByTags(invoiceRequest, tagId,user.getCompanyName());
    }
//    @GetMapping("/transaction")
//    public PagedResponse<InvoiceResponse, Integer> getTransaction(@AuthenticationPrincipal UserEntity user,
//                                                                  @RequestParam(required = false,defaultValue = "2000-01-01 01:01:01") String startDate,
//                                                                  @RequestParam(required = false,defaultValue = "2030-01-01 01:01:01") String endDate,
//                                                                  @RequestParam(required = false) Boolean  status,
//                                                                  @RequestParam(required = false) TypeOfPay typeOfPay,
//                                                                  @RequestParam(required = false) Long categoryId,
//                                                                  @RequestParam int page, @RequestParam int size){
//        return invoiceService.transaction(startDate,endDate,status,typeOfPay,categoryId,size,page,user.getCompanyName().getCompany_id());
//    }
    @GetMapping("/by-client-id/{id}")
    public ClientInvoicesResponse getAllByClientId(@AuthenticationPrincipal UserEntity user,
                                                   @RequestParam Long clientId,
                                                   @RequestParam(required = false,defaultValue = "2000-01-01 01:01:01") String startDate,
                                                   @RequestParam(required = false,defaultValue = "2030-01-01 01:01:01") String endDate,
                                                   @RequestParam(required = false)InvoiceStatus status){
        return invoiceService.getAllByClientId(clientId,startDate,endDate,status,user.getCompanyName().getCompany_id());
    }
}
