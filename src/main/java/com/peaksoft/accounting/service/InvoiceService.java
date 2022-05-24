package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.db.entity.ClientEntity;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.repository.ClientRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.db.repository.TagRepository;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.InvoiceRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;
    private final InvoiceRequestValidator invoiceRequestValidator;
    private final TagRepository tagRepository;

    public InvoiceResponse create(InvoiceRequest request, InvoiceEntity invoice) {
        invoiceRequestValidator.validate(invoice, request);
        InvoiceEntity invoiceEntity = mapToEntity(request, null);
        invoiceRepository.save(invoiceEntity);
        return mapToResponse(invoiceEntity);
    }

    public InvoiceResponse getById(Long id) {
        Optional<InvoiceEntity> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.INVOICE_NOT_FOUND);
        }
        return mapToResponse(invoice.get());
    }

    public InvoiceResponse deleteById(Long id) {
        Optional<InvoiceEntity> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.INVOICE_NOT_FOUND);
        }
        invoiceRepository.deleteById(id);
        return mapToResponse(invoice.get());
    }

    public InvoiceResponse update(InvoiceRequest request, Long id) {
        Optional<InvoiceEntity> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.INVOICE_NOT_FOUND);
        }
        return mapToResponse(invoiceRepository.save(mapToEntity(request, id)));
    }

    public PagedResponse<InvoiceResponse, Integer> findAll(int page, int size, Long clientId, String status, String start, String end, Long invoiceNumber, Boolean isIncome) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<InvoiceResponse> responses = new ArrayList<>();
        Page<InvoiceEntity> pageAble = invoiceRepository.findAllByPagination(clientId, status.toUpperCase(), startDate, endDate, invoiceNumber, PageRequest.of(page - 1, size), isIncome);
        List<InvoiceEntity> invoices = pageAble.getContent();
        for (InvoiceEntity invoice : invoices) {
            responses.add(mapToResponse(invoice));
        }
        PagedResponse<InvoiceResponse, Integer> response = new PagedResponse<>();
        response.setResponses(responses);
        response.setTotalPage(pageAble.getTotalPages());
        return response;
    }

        public PagedResponse<InvoiceResponse, Integer> findAllTransaction(int page, int size, String start, String end,String category, TypeOfPay typeOfPay, Boolean isIncome) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<InvoiceResponse> responses = new ArrayList<>();
        Page<InvoiceEntity> pageAble = invoiceRepository.findAllTransaction(startDate,endDate,typeOfPay,category.toUpperCase(),PageRequest.of(page-1,size),isIncome);
        List<InvoiceEntity> invoices = pageAble.getContent();
        for (InvoiceEntity invoice : invoices) {
            responses.add(mapToResponse(invoice));
        }
        PagedResponse<InvoiceResponse, Integer> response = new PagedResponse<>();
        response.setResponses(responses);
        response.setTotalPage(pageAble.getTotalPages());
        return response;
    }

    public InvoiceResponse sendByTags(InvoiceRequest request, Long tagId) {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if (tag.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.TAG_NOT_FOUND);
        }
        TagEntity tagEntity = tag.get();
        for (ClientEntity client : tagEntity.getClients()) {
            InvoiceEntity invoiceEntity = mapToEntity(request, null);
            invoiceEntity.addClient(client);
            invoiceRepository.save(invoiceEntity);
        }
        return mapToResponse(mapToEntity(request, null));
    }

    public InvoiceEntity mapToEntity(InvoiceRequest request, Long id) {
        InvoiceEntity invoice = new InvoiceEntity();
        Double sum = 0d;
        invoice.setId(id);
        invoice.setTitle(request.getInvoiceTitle());
        if (request.getClientId() != null) {
            Optional<ClientEntity> client = clientRepository.findById(request.getClientId());
            if (client.isEmpty()) {
                throw new ValidationException(ValidationExceptionType.CLIENT_NOT_FOUND);
            }
            invoice.addClient(client.get());
        }
        for (Long productId : request.getProductsId()) {
            Optional<ProductEntity> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new ValidationException((ValidationExceptionType.PRODUCT_NOT_FOUND));
            }
            invoice.addProduct(product.get());
            sum += product.get().getPrice();
        }
        invoice.setStartDate(LocalDateTime.parse(request.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        invoice.setEndDate(LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        invoice.setSum(sum);
        return invoice;
    }

    public InvoiceResponse mapToResponse(InvoiceEntity invoice) {
        return InvoiceResponse.builder()
                .invoiceId(invoice.getId())
                .invoiceTitle(invoice.getTitle())
                .client(clientService.mapToResponse(invoice.getClient()))
                .startDate(invoice.getStartDate())
                .endDate(invoice.getEndDate())
                .products(productService.mapToResponse(invoice.getProducts()))
                .status(invoice.getStatus().getInvoiceStatus())
                .sum(invoice.getSum())
                .build();
    }
}
