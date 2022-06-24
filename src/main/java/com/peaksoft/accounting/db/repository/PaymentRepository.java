package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.PaymentEntity;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("select p from PaymentEntity p left join p.invoice.products pr where " +
            "(p.paymentDate between :starDate and :endDate) and " +
            "(p.typeOfPay=:typeOfPay or :typeOfPay is null)and" +
            "(p.invoice.isIncome=:status or :status is null)and" +
            "(pr.category.id=:categoryId or :categoryId is null)and" +
            "(p.invoice.status=:s1 or p.invoice.status=:s2)")
    Page<PaymentEntity> findAllTransaction(LocalDateTime startDate,LocalDateTime endDate,Boolean status,Long categoryId,InvoiceStatus s1,InvoiceStatus s2,TypeOfPay typeOfPay,Pageable pageable);
}
