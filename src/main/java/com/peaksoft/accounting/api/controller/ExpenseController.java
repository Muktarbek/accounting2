package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/expenses")
@CrossOrigin
public class ExpenseController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all expense", description = "Getting all existing expenses in application")
    public PagedResponse<ProductResponse, Integer> getAll(@RequestParam int page, @RequestParam int size){
        return productService.getAllProducts(page,size,false);
    }
    @PostMapping
    @Operation(summary = "Create expense", description = "Creating a new expense to the existing company in application")
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request,false);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update expense", description = "Updating an existing expense by \"id\" in application")
    public ProductResponse update(@PathVariable Long id,@RequestBody ProductRequest request){
        return productService.update(request,id,false);
    }
    @GetMapping("{id}")
    @Operation(summary = "Get expense", description = "Get an existing expense by \"id\" in application")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete expense", description = "Delete an existing expense by \"id\" in application")
    public ProductResponse deleteById(@PathVariable Long id){
        System.out.println(id);
        return productService.deleteById(id);
    }
}
