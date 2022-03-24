package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<ProductResponse> getAll(@RequestParam int page,@RequestParam int size){
        return productService.getAllProducts(page,size);
    }
    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request);
    }
    @PutMapping("{id}")
    public ProductResponse update(@PathVariable Long id,@RequestBody ProductRequest request){
        return productService.update(request,id);
    }
    @GetMapping("{id}")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }
    @DeleteMapping("{id}")
    public ProductResponse deleteById(@PathVariable Long id){
        return productService.deleteById(id);
    }
}
