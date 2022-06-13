package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Getting all existing products in application")
    public PagedResponse<ProductResponse, Integer> getAll(@RequestParam int page, @RequestParam int size){
        return productService.getAllProducts(page,size,true);
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Creating a new product to the existing company in application")
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request,true);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update product", description = "Updating an existing product by \"id\" in application")
    public ProductResponse update(@PathVariable Long id,@RequestBody ProductRequest request){
        return productService.update(request,id,true);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get product", description = "Get an existing product by \"id\" in application")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete product", description = "Delete an existing product by \"id\" in application")
    public ProductResponse deleteById(@PathVariable Long id){
        System.out.println(id);
        return productService.deleteById(id);
    }

    @Operation(summary = "Notification", description = " ")
    @GetMapping("notification")
    public List<ProductResponse> getNotification(){
        return productService.getNotification();
    }
}
