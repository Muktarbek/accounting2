package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.*;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.PaymentEntity;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.repository.BankAccountRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.PaymentRepository;;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BankAccountRepository bankAccountRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final ClientService clientService;
    private final ProductService productService;

    public PaymentResponse createForProduct(PaymentRequest request,Boolean isIncome) {
        ProductEntity product = productRepository.findById(request.getProductId()).orElseThrow(()->new ValidationException("not found product  "+request.getProductId()));
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setSum(product.getPrice());
        invoice.addProduct(product);
        PaymentEntity payment = paymentRepository.save(mapToEntity(request));
        invoice.setLastDateOfPayment(payment.getPaymentDate());
        invoice.setStatus(InvoiceStatus.PAID);
        if(product.getPrice()!= request.getAmountOfMoney()){
            throw new ValidationException(ValidationExceptionType.EXCEEDS_THE_AMOUNT_PER_PRODUCT);
        }
        invoice.setRestAmount(product.getPrice()- request.getAmountOfMoney());
        invoice.setSum(payment.getAmountOfMoney());
        invoice.setIsIncome(isIncome);
        invoice.setTitle("Payment for Product");
        invoice.setStartDate(LocalDateTime.now());
        invoice.setEndDate(LocalDateTime.now());
        invoice.addPayment(payment);
        invoiceRepository.save(invoice);
        return mapToResponse(payment);
    }
    public PaymentResponse create(long invoiceId, PaymentRequest paymentRequest) {
        PaymentEntity payment = mapToEntity(paymentRequest);
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId).get();
        double productPrice = invoice.getRestAmount();
        double paymentSum = paymentRequest.getAmountOfMoney();
        if (productPrice > paymentSum && paymentSum > 0) {
            double amount = productPrice - paymentSum;
            invoice.setRestAmount(amount);
            invoice.setStatus(InvoiceStatus.PARTIALLY);
        }
        if (productPrice == paymentSum) {
            double amount = productPrice - paymentSum;
            invoice.setRestAmount(amount);
            invoice.setStatus(InvoiceStatus.PAID);
        }
        if(productPrice<paymentSum){
            throw new ValidationException(ValidationExceptionType.EXCEEDS_THE_AMOUNT_PER_PRODUCT);
        }
        invoice.setLastDateOfPayment(payment.getPaymentDate());
        payment.setInvoice(invoice);
        invoiceRepository.save(invoice);
        return mapToResponse(paymentRepository.save(payment));
    }

    public PaymentResponse update(long id, PaymentRequest paymentRequest) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.NOT_FOUND);
        }
        mapToUpdate(payment.get(), paymentRequest);
        return mapToResponse(paymentRepository.save(payment.get()));
    }

    public PaymentResponse delete(long id) {
        PaymentEntity payment = paymentRepository.findById(id).get();
        paymentRepository.delete(payment);
        return mapToResponse(payment);
    }

    public PaymentResponse findById(long id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.NOT_FOUND);
        }
        return mapToResponse(paymentRepository.findById(id).get());
    }

    public List<PaymentResponse> gelAllPayments() {
        return map(paymentRepository.findAll());
    }
    public PagedResponse<PaymentResponse, Integer> transaction(String start,
                                                               String end,
                                                               Boolean status,
                                                               TypeOfPay typeOfPay,
                                                               Long categoryId,
                                                               int size, int page) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<PaymentResponse> payments = map(paymentRepository.findAllTransaction(startDate,endDate,status,categoryId,InvoiceStatus.PAID,InvoiceStatus.PARTIALLY,typeOfPay, PageRequest.of(page-1,size)).getContent());
        PagedResponse<PaymentResponse,Integer> response = new PagedResponse();
        response.setResponses(payments);
        response.setTotalPage(paymentRepository.findAllTransaction(startDate,endDate,status,categoryId,InvoiceStatus.PAID,InvoiceStatus.PARTIALLY,typeOfPay, PageRequest.of(page-1,size)).getTotalPages());
        return response;
    }

    private PaymentEntity mapToEntity(PaymentRequest paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentDate(LocalDateTime.parse(paymentRequest.getPaymentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(paymentRequest.getPaymentFile());
        payment.setAmountOfMoney(paymentRequest.getAmountOfMoney());
        if(paymentRequest.getTypeOfPay()!= TypeOfPay.CASH){
        BankAccountEntity bankAccount = bankAccountRepository.findById(paymentRequest.getBankAccount()).get();
        payment.setBankAccount(bankAccount);}
        payment.setTypeOfPay(paymentRequest.getTypeOfPay());
        payment.setComment(paymentRequest.getComment());
        payment.setCreated(LocalDateTime.now());
        return payment;
    }

    private PaymentEntity mapToUpdate(PaymentEntity payment, PaymentRequest paymentRequest) {
        payment.setPaymentDate(LocalDateTime.parse(paymentRequest.getPaymentDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(paymentRequest.getPaymentFile());
        payment.setAmountOfMoney(paymentRequest.getAmountOfMoney());
        payment.setTypeOfPay(paymentRequest.getTypeOfPay());
        BankAccountEntity bankAccount = bankAccountRepository.findById(paymentRequest.getBankAccount()).get();
        payment.setBankAccount(bankAccount);
        payment.setComment(paymentRequest.getComment());
        return payment;
    }

    private PaymentResponse mapToResponse(PaymentEntity payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPayment_id())
                .bankAccount(payment.getBankAccount())
                .paymentDate(payment.getPaymentDate())
                .paymentFile(payment.getPaymentFile())
                .typeOfPay(payment.getTypeOfPay())
                .amountOfMoney(payment.getAmountOfMoney())
                .comment(payment.getComment())
                .created(payment.getCreated())
                .invoice(mapToResponse(payment.getInvoice()))
                .build();
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
                .restAmount(invoice.getRestAmount())
                .isIncome(invoice.getIsIncome())
                .build();
    }

    private List<PaymentResponse> map(List<PaymentEntity> payments) {
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (PaymentEntity payment : payments) {
            paymentResponses.add(mapToResponse(payment));
        }
        return paymentResponses;
    }
}
