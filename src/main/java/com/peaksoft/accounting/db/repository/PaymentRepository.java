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
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("select p from PaymentEntity p left join p.invoice.products pr where " +
            "p.company.company_id=:companyId and " +
            "(p.paymentDate between :startDate and :endDate) and " +
            "(p.typeOfPay =:typeOfPay or :typeOfPay is null) and " +
            "(p.invoice.isIncome =:status or :status is null) and " +
            "(pr.category.id =:categoryId or :categoryId is null) and " +
            "(p.invoice.status =:s1 or p.invoice.status =:s2)")
    Page<PaymentEntity> findAllTransaction(LocalDateTime startDate,LocalDateTime endDate,Boolean status,Long categoryId,
                                           InvoiceStatus s1,InvoiceStatus s2,TypeOfPay typeOfPay,Pageable pageable,Long companyId);
    @Query("select p from  PaymentEntity  p where p.company.company_id=:companyId")
    List<PaymentEntity> findAllByCompany(Long companyId);
}
