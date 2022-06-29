package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.*;
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
@RequestMapping("/api/myaccount/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Getting all existing products in application")
    public PagedResponse<ProductResponse, Integer> getAll(@AuthenticationPrincipal UserEntity user,@RequestParam int page, @RequestParam int size){
        return productService.getAllProducts(page,size,true,user.getCompanyName().getCompany_id());
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts(@AuthenticationPrincipal UserEntity user){
        return productService.getAllProducts(true,user.getCompanyName().getCompany_id());
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Creating a new product to the existing company in application")
    public ProductResponse save(@AuthenticationPrincipal UserEntity user,@RequestBody ProductRequest request){
        return productService.save(request,true,user.getCompanyName());
    }

    @PutMapping("{id}")
    @Operation(summary = "Update product", description = "Updating an existing product by \"id\" in application")
    public ProductResponse update(@AuthenticationPrincipal UserEntity user,@PathVariable Long id,@RequestBody ProductRequest request){
        return productService.update(request,id,true,user.getCompanyName());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get product", description = "Get an existing product by \"id\" in application")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete product", description = "Delete an existing product by \"id\" in application")
    public ProductResponse deleteById(@PathVariable Long id){
        return productService.deleteById(id);
    }

//    @Operation(summary = "Notification", description = " ")
//    @GetMapping("notification")
//    public List<ProductResponse> getNotification(){
//        return productService.getNotification();
//    }

    @GetMapping("/search/by/name")
    public List<ProductResponse> searchByName(@AuthenticationPrincipal UserEntity user,@RequestParam String tagName){
        return productService.searchByName(tagName,true,user.getCompanyName().getCompany_id());
    }
    @GetMapping("/find/all")
    public List<ProductResponse> findAll(@AuthenticationPrincipal UserEntity user){
        return productService.getAll(true,user.getCompanyName().getCompany_id());
    }
}
