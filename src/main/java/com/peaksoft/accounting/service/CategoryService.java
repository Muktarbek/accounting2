package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.CategoryRequest;
import com.peaksoft.accounting.api.payload.CategoryResponse;
import com.peaksoft.accounting.db.entity.CategoryEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories(boolean flag){
         return mapToResponse(categoryRepository.findAllByIsIncomeCategory(flag));
    }
    public CategoryResponse save(CategoryRequest request){
        return mapToResponse(categoryRepository.save(mapToEntity(request,null)));
    }
    public CategoryResponse getById(Long id){
        return mapToResponse(categoryRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Category with id - %s, not found", id))
                ));
    }
    public CategoryResponse deleteById(Long id){
        CategoryResponse response = mapToResponse(categoryRepository.findById(id).get());
        categoryRepository.deleteById(id);
        return response;
    }
    public CategoryResponse update(Long id,CategoryRequest request){
        return mapToResponse(categoryRepository.save(mapToEntity(request,id)));
    }
    public CategoryEntity mapToEntity(CategoryRequest request,Long id){
        return CategoryEntity.builder()
                .id(id)
                .title(request.getCategoryTitle())
                .description(request.getCategoryDescription())
                .isIncomeCategory(request.getFlag())
                .build();
    }
    public CategoryResponse mapToResponse(CategoryEntity category){
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .categoryTitle(category.getTitle())
                .categoryDescription(category.getDescription())
                .build();
    }
    public List<CategoryResponse> mapToResponse(List<CategoryEntity> categories){
        List<CategoryResponse> responses = new ArrayList<>();
        for (CategoryEntity category : categories) {
            responses.add(mapToResponse(category));
        }
        return responses;
    }
}
