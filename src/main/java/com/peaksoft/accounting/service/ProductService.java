package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.api.payload.ServiceTypeResponse;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.ServiceTypeEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.db.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public List<ProductResponse> getAllProducts(int page,int size){
        return mapToResponse(productRepository.findAllByPagination( PageRequest.of(page - 1, size)));
    }
    public ProductResponse save(ProductRequest request){
        return mapToResponse(productRepository.save(mapToEntity(request,null)));
    }
    public ProductResponse update(ProductRequest request,Long id){
        return mapToResponse(productRepository.save(mapToEntity(request,id)));
    }
    public ProductResponse getById(Long id){
        return mapToResponse(productRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Category with id - %s, not found", id))
                ));
    }
    public ProductResponse deleteById(Long id){
        ProductResponse response = mapToResponse(productRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Product with id - %s, not found", id))
                ));
        productRepository.deleteById(id);
        return response;
    }

    public ProductEntity mapToEntity(ProductRequest request,Long id){
        return ProductEntity.builder()
                .id(id)
                .title(request.getProductTitle())
                .price(request.getProductPrice())
                .description(request.getProductDescription())
                .serviceType(serviceTypeRepository.findById(request.getServiceTypeId()).get())
                .category(categoryRepository.findById(request.getCategoryId()).get())
                .build();
    }
    public ProductResponse mapToResponse(ProductEntity product){
        return ProductResponse.builder()
                .productId(product.getId())
                .productTitle(product.getTitle())
                .productPrice(product.getPrice())
                .serviceType(mapToServiceResponse(product.getServiceType()))
                .category(categoryService.mapToResponse(product.getCategory()))
                .productDescription(product.getDescription())
                .build();
    }
    public List<ProductResponse> mapToResponse(List<ProductEntity> products){
        List<ProductResponse> responses = new ArrayList<>();
        for (ProductEntity product : products) {
            responses.add(mapToResponse(product));
        }
        return responses;
    }
    public ServiceTypeResponse mapToServiceResponse(ServiceTypeEntity serviceType){
        return ServiceTypeResponse.builder()
                .service_type_id(serviceType.getId())
                .service_type(serviceType.getServiceType().getServiceType())
                .build();
    }
}
