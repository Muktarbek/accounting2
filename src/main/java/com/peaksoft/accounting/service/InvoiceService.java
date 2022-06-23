package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.ClientInvoicesResponse;
import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.db.entity.*;
import com.peaksoft.accounting.db.repository.*;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.InvoiceRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InvoiceRequestValidator invoiceRequestValidator;
    private final TagRepository tagRepository;

    public InvoiceResponse create(InvoiceRequest request, InvoiceEntity invoice,Boolean isIncome) {
        invoiceRequestValidator.validate(invoice, request);
        InvoiceEntity invoiceEntity = mapToEntity(request, invoice,null);
        invoiceEntity.setIsIncome(isIncome);
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
        invoice.get().getProducts().forEach(p->p.getInvoices().remove(invoice));
        System.out.println(invoice.get().getProducts().size());
//        productRepository.saveAll(invoice.get().getProducts());
        invoiceRepository.delete(invoice.get());
        return mapToResponse(invoice.get());
    }

    public InvoiceResponse update(InvoiceRequest request, Long id) {
        Optional<InvoiceEntity> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.INVOICE_NOT_FOUND);
        }
        return mapToResponse(invoiceRepository.save(mapToEntity(request,invoice.get(), id)));
    }

    public PagedResponse<InvoiceResponse, Integer> findAll(int page, int size, Long clientId, String status, String start, String end, Long invoiceNumber, Boolean isIncome) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<InvoiceResponse> responses = new ArrayList<>();
        Page<InvoiceEntity> pageAble = invoiceRepository.findAllByPagination(clientId, status.toUpperCase(), startDate, endDate, invoiceNumber, PageRequest.of(page - 1, size),InvoiceStatus.PAID, isIncome);
        List<InvoiceEntity> invoices = pageAble.getContent();
        for (InvoiceEntity invoice : invoices) {
            responses.add(mapToResponse(invoice));
        }
        LocalDateTime starDate = LocalDateTime.now().minusDays(30);
        PagedResponse<InvoiceResponse, Integer> response = new PagedResponse<>();
        response.setResponses(responses);
        response.setPaymentAmountOverdue(invoiceRepository.getSumDays(LocalDateTime.now().minusYears(100),LocalDateTime.now(),InvoiceStatus.EXPIRED,isIncome));
        response.setPaymentAmountDays(invoiceRepository.getSumDays(starDate,LocalDateTime.now(),InvoiceStatus.PARTIALLY,isIncome));
        response.setTotalPage(pageAble.getTotalPages());
        return response;
    }
    public InvoiceResponse sendByTags(InvoiceRequest request, Long tagId) {
        InvoiceEntity invoice = new InvoiceEntity();
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if (tag.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.TAG_NOT_FOUND);
        }
        TagEntity tagEntity = tag.get();
        for (ClientEntity client : tagEntity.getClients()) {
            InvoiceEntity invoiceEntity = mapToEntity(request, invoice,null);
            invoiceEntity.addClient(client);
            invoiceRepository.save(invoiceEntity);
        }
        return mapToResponse(mapToEntity(request, invoice,null));
    }
    public PagedResponse<InvoiceResponse,Integer>transaction(String start,
                                             String end,
                                             Boolean status,
                                             TypeOfPay typeOfPay,
                                             Long categoryId,
                                             int size,int page) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<InvoiceResponse> invoices = mapToResponse(invoiceRepository.findAllTransaction(startDate,endDate,status,categoryId,InvoiceStatus.PAID,typeOfPay,PageRequest.of(page-1,size)).getContent());
        for(int i = 0; i < invoices.size(); i++) {
            if(typeOfPay!=null){
            invoices.get(i).setTypeOfPay(typeOfPay.toString());}
            invoices.get(i).setClient(null);
        }
        PagedResponse<InvoiceResponse,Integer> pages= new PagedResponse<>();
        pages.setResponses(invoices);
        pages.setTotalPage(invoiceRepository.findAllTransaction(startDate,endDate,status,categoryId,InvoiceStatus.PAID,typeOfPay,PageRequest.of(page-1,size)).getTotalPages());
        return pages;
    }

    public ClientInvoicesResponse getAllByClientId(Long clientId, String start, String end, InvoiceStatus status) {
        ClientInvoicesResponse response = new ClientInvoicesResponse();
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.setInvoices(mapToResponse(invoiceRepository.getAllByClientId(clientId,startDate,endDate,status)));
        return response;
    }

    public InvoiceEntity mapToEntity(InvoiceRequest request,InvoiceEntity invoice, Long id) {
        Double sum = 0d;
        invoice.setDescription(request.getDescription());
        invoice.setId(id);
        invoice.setTitle(request.getInvoiceTitle());
        invoice.setProducts(null);
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
        invoice.setDiscount(request.getDiscount());
        invoice.setStartDate(LocalDateTime.parse(request.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        invoice.setEndDate(LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        invoice.setSum(request.getSum());
        invoice.setRestAmount(request.getSum());
        return invoice;
    }

    public InvoiceResponse mapToResponse(InvoiceEntity invoice) {
        return InvoiceResponse.builder()
                .invoiceId(invoice.getId())
                .description(invoice.getDescription())
                .invoiceTitle(invoice.getTitle())
                .client(clientService.mapToResponse(invoice.getClient()))
                .lastDateOfPayment(invoice.getLastDateOfPayment())
                .startDate(invoice.getStartDate())
                .endDate(invoice.getEndDate())
                .products(productService.mapToResponse(invoice.getProducts()))
                .status(invoice.getStatus().getInvoiceStatus())
                .sum(invoice.getSum())
                .discount(invoice.getDiscount())
                .build();
    }
   public List<InvoiceResponse> mapToResponse(List<InvoiceEntity> invoices){
        return invoices.stream().map(this::mapToResponse).collect(Collectors.toList());
   }


}
