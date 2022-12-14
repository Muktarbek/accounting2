package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.CategoryRequest;
import com.peaksoft.accounting.api.payload.CategoryResponse;
import com.peaksoft.accounting.db.entity.CategoryEntity;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    public List<CategoryResponse> getAllCategories(Long companyId,boolean flag){
         return mapToResponse(categoryRepository.findAllByIsIncomeCategory(companyId,flag));
    }

    public List<CategoryResponse> getAllCategoriesWithoutFlag(Long companyId){
        return mapToResponse(categoryRepository.findAllByCompany(companyId));
    }

    public CategoryResponse save(CategoryRequest request,CompanyEntity company){
        return mapToResponse(categoryRepository.save(mapToEntity(request,null,company)));
    }

    public CategoryResponse getById(Long id){
        return mapToResponse(categoryRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Category with id - %s, not found", id))
                ));
    }

    public CategoryResponse deleteById(Long id){
        CategoryEntity category = categoryRepository.findById(id).get();
        CategoryResponse response = mapToResponse(category);
        category.getProducts().forEach(p->p.setCategory(null));
        productRepository.saveAll(category.getProducts());
        categoryRepository.deleteById(id);
        return response;
    }

    public CategoryResponse update(Long id,CategoryRequest request,CompanyEntity company){
        return mapToResponse(categoryRepository.save(mapToEntity(request,id,company)));
    }

    public CategoryEntity mapToEntity(CategoryRequest request, Long id,CompanyEntity  company){
        return CategoryEntity.builder()
                .id(id)
                .title(request.getCategoryTitle())
                .description(request.getCategoryDescription())
                .isIncomeCategory(request.getFlag())
                .company(company)
                .build();
    }

    public CategoryResponse mapToResponse(CategoryEntity category){
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .categoryTitle(category.getTitle())
                .categoryDescription(category.getDescription())
                .flag(category.getIsIncomeCategory())
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
