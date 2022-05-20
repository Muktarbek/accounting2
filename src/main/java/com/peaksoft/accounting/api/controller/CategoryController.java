package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.CategoryRequest;
import com.peaksoft.accounting.api.payload.CategoryResponse;
import com.peaksoft.accounting.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/income")
    @Operation(summary = "Get all categories", description = "Getting all existing categories")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories(true);
    }
    @GetMapping("/expense")
    public List<CategoryResponse> getAllExpenseCategories(){
        return categoryService.getAllCategories(false);
    }
    @GetMapping("{id}")
    @Operation(summary = "Get category", description = "Getting an existing category by \"id\" in application ")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getById(id);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete category", description = "Delete an existing category by \"id\" in application")
    public CategoryResponse deleteById(@PathVariable Long id){
        return categoryService.deleteById(id);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update category", description = "Update a new category by \"id\" in application ")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request){
        return categoryService.update(id,request);
    }
    @PostMapping
    @Operation(summary = "Create category", description = "Creating a new category")
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }
}
