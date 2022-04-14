package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.SellerRequest;
import com.peaksoft.accounting.api.payload.SellerResponse;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/sellers")
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
public class SellerController {


    private final SellerService sellerService;

    @PostMapping
    @Operation(summary = "Create seller", description = "Creating a new seller")
    public SellerResponse create(@RequestBody @Valid SellerRequest sellerRequest, ClientEntity seller) {
        return sellerService.create(seller, sellerRequest);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update seller", description = "Update a new seller by \"id\" in application ")
    public SellerResponse update(@PathVariable long id, @RequestBody SellerRequest sellerRequest) {
        return sellerService.update(id, sellerRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete seller", description = "delete an existing seller by \"id\" in application ")
    public SellerResponse delete(@PathVariable long id) {
        return sellerService.deleteById(id);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get seller", description = "Getting an existing seller by \"id\" in application ")
    public SellerResponse getById(@PathVariable long id) {
        return sellerService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get all seller", description = "Getting all existing seller")
    public PagedResponse<SellerResponse, Integer> getAllSeller(@RequestParam int page,int size) {
        return sellerService.findAll(page,size);
    }


}
