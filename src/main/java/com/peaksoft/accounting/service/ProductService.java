package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.*;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.ServiceTypeEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.db.repository.ServiceTypeRepository;
import com.peaksoft.accounting.enums.ReminderType;
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
    private final ReminderService reminderService;

    public PagedResponse<ProductResponse, Integer> getAllProducts(int page, int size, boolean flag,Long companyId) {
        Page<ProductEntity> pages = productRepository.findAllByPagination(PageRequest.of(page - 1, size), flag,companyId);
        PagedResponse<ProductResponse, Integer> response = new PagedResponse<>();
        response.setResponses(mapToResponse(pages.getContent()));
        response.setTotalPage(pages.getTotalPages());
        return response;
    }

    public List<ProductResponse> getAllProducts(boolean flag,Long companyId) {
        List<ProductEntity> product = productRepository.findAllByPagination(flag,companyId);
        return mapToResponse(product);
    }

    public ProductResponse save(ProductRequest request, boolean flag, CompanyEntity company) {
        return mapToResponse(productRepository.save(mapToEntity(request, null, flag,company)));
    }

    public ProductResponse update(ProductRequest request, Long id, boolean flag,CompanyEntity company) {
        return mapToResponse(productRepository.save(mapToEntity(request, id, flag,company)));
    }

    public ProductResponse getById(Long id) {
        return mapToResponse(productRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("Product with id - %s, not found", id))
                ));
    }

    public ProductResponse deleteById(Long id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.PRODUCT_NOT_FOUND);
        }
        ProductEntity product = optionalProduct.get();
        product.getInvoices().forEach(u -> u.getProducts().remove(product));
        invoiceRepository.saveAll(product.getInvoices());
        productRepository.delete(product);
        return mapToResponse(product);
    }

    public List<ProductResponse> searchByName(String title,Boolean flag,Long companyId ) {
        return mapToResponse(productRepository.searchAllByTitle(title,flag,companyId));
    }
    public List<ProductResponse> getAll(Boolean  flag,Long companyId) {
        return mapToResponse(productRepository.getAll(flag,companyId));
    }
    public ProductEntity mapToEntity(ProductRequest request, Long id, boolean flag,CompanyEntity company) {
        return ProductEntity.builder()
                .id(id)
                .title(request.getProductTitle())
                .price(request.getProductPrice())
                .description(request.getProductDescription())
                .serviceType(serviceTypeRepository.findById(request.getServiceTypeId()).get())
                .category(categoryRepository.findById(request.getCategoryId()).get())
                .isIncome(flag)
                .company(company)
                .build();
    }

    public ProductResponse mapToResponse(ProductEntity product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getId());
        productResponse.setProductDescription(product.getDescription());
        productResponse.setProductPrice(product.getPrice());
        productResponse.setProductTitle(product.getTitle());
        productResponse.setServiceType(mapToServiceResponse(product.getServiceType()));
        if(product.getCategory()!=null)
            productResponse.setCategory(categoryService.mapToResponse(product.getCategory()));
        if (product.getReminder() != null) {
            productResponse.setReminderResponse(reminderService.mapToResponse(product.getReminder()));
        }
        return productResponse;
    }

    public List<ProductResponse> mapToResponse(List<ProductEntity> products) {
        List<ProductResponse> responses = new ArrayList<>();
        for (ProductEntity product : products) {
            responses.add(mapToResponse(product));
        }
        return responses;
    }

    public ServiceTypeResponse mapToServiceResponse(ServiceTypeEntity serviceType) {
        return ServiceTypeResponse.builder()
                .serviceTypeId(serviceType.getId())
                .serviceType(serviceType.getServiceType().getServiceType())
                .build();
    }

//    public List<ProductResponse> getNotification() {
//        List<ProductResponse> getNotification = new ArrayList<>();
//        for (ProductEntity p : productRepository.findAllByIsIncome(false)) {
//            if (p.getReminder() != null) {
//                if (p.getReminderType() == ReminderType.PAY_FOR || p.getReminderType() == ReminderType.EXPIRED) {
//                    getNotification.add(mapToResponse(p));
//                }
//            }
//        }
//        return getNotification;
//    }
}
