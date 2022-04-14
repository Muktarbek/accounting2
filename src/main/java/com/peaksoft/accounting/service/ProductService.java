package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.ProductRequest;
import com.peaksoft.accounting.api.payload.ProductResponse;
import com.peaksoft.accounting.api.payload.ServiceTypeResponse;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.ServiceTypeEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.db.repository.ServiceTypeRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final InvoiceRepository invoiceRepository;

    public PagedResponse<ProductResponse,Integer> getAllProducts(int page, int size, boolean flag){
     Page<ProductEntity> pages = productRepository.findAllByPagination( PageRequest.of(page - 1, size),flag);
       PagedResponse<ProductResponse,Integer> response = new PagedResponse<>();
       response.setResponses(mapToResponse(pages.getContent()));
       response.setTotalPage(pages.getTotalPages());
        return response;
    }
    public ProductResponse save(ProductRequest request,boolean flag){
        return mapToResponse(productRepository.save(mapToEntity(request,null,flag)));
    }
    public ProductResponse update(ProductRequest request,Long id,boolean flag){
        return mapToResponse(productRepository.save(mapToEntity(request,id,flag)));
    }
    public ProductResponse getById(Long id){
        return mapToResponse(productRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Product with id - %s, not found", id))
                ));
    }
    public ProductResponse deleteById(Long id){
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ValidationException(ValidationExceptionType.PRODUCT_NOT_FOUND);
        }
        ProductEntity product = optionalProduct.get();
        product.getInvoices().forEach(u -> u.getProducts().remove(product));
        invoiceRepository.saveAll(product.getInvoices());
        productRepository.delete(product);
        return mapToResponse(product);
    }

    public ProductEntity mapToEntity(ProductRequest request,Long id,boolean flag){
        return ProductEntity.builder()
                .id(id)
                .title(request.getProductTitle())
                .price(request.getProductPrice())
                .description(request.getProductDescription())
                .serviceType(serviceTypeRepository.findById(request.getServiceTypeId()).get())
                .category(categoryRepository.findById(request.getCategoryId()).get())
                .isIncome(flag)
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
                .serviceTypeId(serviceType.getId())
                .serviceType(serviceType.getServiceType().getServiceType())
                .build();
    }
}
