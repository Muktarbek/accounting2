package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.PaymentEntity;
import com.peaksoft.accounting.enums.TypeOfPay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("select p from PaymentEntity p inner join p.category c where c.id = :category or :category is null " +
            "and p.typeOfPay = :typeOfPay or :typeOfPay is null " +
            "and p.status ='PAID'")
    Page<PaymentEntity> findAllTransaction(Long category, Pageable pageable, TypeOfPay typeOfPay);
}
