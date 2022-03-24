package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.CategoryRequest;
import com.peaksoft.accounting.api.payload.CategoryResponse;
import com.peaksoft.accounting.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @GetMapping("{id}")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getById(id);
    }
    @DeleteMapping("{id}")
    public CategoryResponse deleteById(@PathVariable Long id){
        return categoryService.deleteById(id);
    }
    @PutMapping("{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request){
        return categoryService.update(id,request);
    }
    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }
}
