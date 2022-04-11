package com.peaksoft.accounting.service;

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

    public ProductEntity mapToEntity(ProductRequest request,Long id){
        return ProductEntity.builder()
                .product_id(id)
                .title(request.getProduct_title())
                .price(request.getProduct_price())
                .description(request.getProduct_description())
                .serviceType(serviceTypeRepository.findById(request.getService_type_id()).get())
                .category(categoryRepository.findById(request.getCategory_id()).get())
                .build();
    }
    public ProductResponse mapToResponse(ProductEntity product){
        return ProductResponse.builder()
                .productId(product.getProduct_id())
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
                .service_type_id(serviceType.getServiceType_id())
                .service_type(serviceType.getServiceType().getServiceType())
                .build();
    }
}
