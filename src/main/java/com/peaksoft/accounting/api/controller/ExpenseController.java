package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@RequestMapping("/api/myaccount/expenses")
@CrossOrigin
public class ExpenseController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all expense", description = "Getting all existing expenses in application")
    public PagedResponse<ProductResponse, Integer> getAll(@AuthenticationPrincipal UserEntity user,@RequestParam int page, @RequestParam int size){
        return productService.getAllProducts(page,size,false,user.getCompanyName().getCompany_id());
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts(@AuthenticationPrincipal UserEntity user){
        return productService.getAllProducts(false,user.getCompanyName().getCompany_id());
    }

    @PostMapping
    @Operation(summary = "Create expense", description = "Creating a new expense to the existing company in application")
    public ProductResponse save(@AuthenticationPrincipal UserEntity user,@RequestBody ProductRequest request){
        return productService.save(request,false,user.getCompanyName());
    }
    @PutMapping("{id}")
    @Operation(summary = "Update expense", description = "Updating an existing expense by \"id\" in application")
    public ProductResponse update(@AuthenticationPrincipal UserEntity user,@PathVariable Long id,@RequestBody ProductRequest request){
        return productService.update(request,id,false,user.getCompanyName());
    }
    @GetMapping("{id}")
    @Operation(summary = "Get expense", description = "Get an existing expense by \"id\" in application")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete expense", description = "Delete an existing expense by \"id\" in application")
    public ProductResponse deleteById(@PathVariable Long id){
        return productService.deleteById(id);
    }
    @GetMapping("/search/by/name")
    public List<ProductResponse> searchByName(@AuthenticationPrincipal UserEntity user,@RequestParam String tagName){
        return productService.searchByName(tagName,false,user.getCompanyName().getCompany_id());
    }
    @GetMapping("/find/all")
    public List<ProductResponse> findAll(@AuthenticationPrincipal UserEntity user){
        return productService.getAll(false,user.getCompanyName().getCompany_id());
    }
}
