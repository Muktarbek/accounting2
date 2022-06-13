package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.InvoiceResponse;
import com.peaksoft.accounting.api.payload.PagedResponse;
import com.peaksoft.accounting.api.payload.TPRequest;
import com.peaksoft.accounting.api.payload.TPResponse;
import com.peaksoft.accounting.db.entity.CategoryEntity;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.PaymentEntity;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.repository.CategoryRepository;
import com.peaksoft.accounting.db.repository.PaymentRepository;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
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
public class TPService {

    private final PaymentRepository tpRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductService productService;


    public TPResponse create(TPRequest request) {
        PaymentEntity payment = mapToEntity(request);
        ProductEntity product = productRepository.findById(request.getProductId()).get();
        double price = product.getPrice();
        if (request.getAmountOfMoney() != price) {
            throw new ValidationException(ValidationExceptionType.PAY_THE_FULL_AMOUNT);
        }
        payment.setProduct(product);
        payment.setStatus(InvoiceStatus.PAID);
        productRepository.save(product);
        return mapToResponse(tpRepository.save(payment));
    }

    public TPResponse update(long id, TPRequest tpRequest) {
        Optional<PaymentEntity> transaction = tpRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.NOT_FOUND);
        }
        mapToUpdate(transaction.get(), tpRequest);
        return mapToResponse(tpRepository.save(transaction.get()));
    }

    public TPResponse delete(long id) {
        PaymentEntity payment = tpRepository.findById(id).get();
        tpRepository.delete(payment);
        return mapToResponse(payment);
    }

    public TPResponse findById(long id) {
        Optional<PaymentEntity> transaction = tpRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.NOT_FOUND);
        }
        return mapToResponse(tpRepository.findById(id).get());
    }

    public List<TPResponse> gelAllPayments() {
        return map(tpRepository.findAll());
    }

    public PagedResponse<TPResponse, Integer> findAllTransaction(int page, int size, TypeOfPay typeOfPay, Long category) {
//        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<TPResponse> responses = new ArrayList<>();
        Page<PaymentEntity> pageAble = tpRepository.findAllTransaction(category,PageRequest.of(page - 1, size), typeOfPay);
        List<PaymentEntity> payments = pageAble.getContent();
        for (PaymentEntity payment : payments) {
            responses.add(mapToResponse(payment));
        }
        PagedResponse<TPResponse, Integer> response = new PagedResponse<>();
        response.setResponses(responses);
        response.setTotalPage(pageAble.getTotalPages());
        return response;
    }

    private PaymentEntity mapToEntity(TPRequest tpRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentDate(LocalDateTime.parse(tpRequest.getPaymentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(tpRequest.getPaymentFile());
        payment.setAmountOfMoney(tpRequest.getAmountOfMoney());
        payment.setStatus(InvoiceStatus.NOT_PAID);
        ProductEntity product = productRepository.findById(tpRequest.getProductId()).get();
        CategoryEntity category = categoryRepository.findById(tpRequest.getCategoryId()).get();
        payment.setProduct(product);
        payment.setCategory(category);
        payment.setIsIncomePayment(tpRequest.getIsIncomePayment());
        payment.setTypeOfPay(tpRequest.getTypeOfPay());
        payment.setIsIncomePayment(payment.getIsIncomePayment());
        payment.setCreated(LocalDateTime.now());
        return payment;
    }

    private PaymentEntity mapToUpdate(PaymentEntity payment, TPRequest tpRequest) {
        payment.setPaymentDate(LocalDateTime.parse(tpRequest.getPaymentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(tpRequest.getPaymentFile());
//        payment.setAmountOfMoney(tpRequest.getAmountOfMoney());
        payment.setTypeOfPay(tpRequest.getTypeOfPay());
        ProductEntity product = productRepository.findById(tpRequest.getProductId()).get();
        CategoryEntity category = categoryRepository.findById(tpRequest.getCategoryId()).get();
        payment.setProduct(product);
        payment.setCategory(category);
        payment.setTypeOfPay(tpRequest.getTypeOfPay());
        return payment;
    }

    private TPResponse mapToResponse(PaymentEntity payment) {
        return TPResponse.builder()
                .tpId(payment.getPayment_id())
                .paymentDate(payment.getPaymentDate())
                .paymentFile(payment.getPaymentFile())
                .amountOfMoney(payment.getAmountOfMoney())
                .typeOfPay(payment.getTypeOfPay())
                .created(payment.getCreated())
                .status(payment.getStatus())
                .isIncomePayment(payment.getIsIncomePayment())
                .response(productService.mapToResponse(payment.getProduct()))
                .build();
    }

    private List<TPResponse> map(List<PaymentEntity> payments) {
        List<TPResponse> tpResponses = new ArrayList<>();
        for (PaymentEntity payment : payments) {
            tpResponses.add(mapToResponse(payment));
        }
        return tpResponses;
    }
}
