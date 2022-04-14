package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.PaymentRequest;
import com.peaksoft.accounting.api.payload.PaymentResponse;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.PaymentEntity;
import com.peaksoft.accounting.db.repository.BankAccountRepository;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.db.repository.PaymentRepository;;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
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

    public PaymentResponse create(long invoiceId, PaymentRequest paymentRequest) {
        PaymentEntity payment = mapToEntity(paymentRequest);
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId).get();
        double productPrice = invoice.getSum();
        double paymentSum = paymentRequest.getAmountOfMoney();
        if (productPrice > paymentSum) {
            double amount = productPrice - paymentSum;
            invoice.setSum(amount);
            invoice.setStatus(InvoiceStatus.PARTIALLY);
        } else if (productPrice == paymentSum) {
            double amount = productPrice - paymentSum;
            invoice.setSum(amount);
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            throw new ValidationException(ValidationExceptionType.EXCEEDS_THE_AMOUNT_PER_PRODUCT);
        }
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

    private PaymentEntity mapToEntity(PaymentRequest paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentDate(LocalDateTime.parse(paymentRequest.getPaymentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(paymentRequest.getPaymentFile());
        payment.setAmountOfMoney(paymentRequest.getAmountOfMoney());
        BankAccountEntity bankAccount = bankAccountRepository.findById(paymentRequest.getBankAccount()).get();
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setBankAccount(bankAccount);
        payment.setComment(paymentRequest.getComment());
        payment.setCreated(LocalDateTime.now());
        return payment;
    }

    private PaymentEntity mapToUpdate(PaymentEntity payment, PaymentRequest paymentRequest) {
        payment.setPaymentDate(LocalDateTime.parse(paymentRequest.getPaymentDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payment.setPaymentFile(paymentRequest.getPaymentFile());
        payment.setAmountOfMoney(paymentRequest.getAmountOfMoney());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
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
                .paymentMethod(payment.getPaymentMethod())
                .amountOfMoney(payment.getAmountOfMoney())
                .comment(payment.getComment())
                .created(payment.getCreated())
                .invoice(payment.getInvoice())
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
