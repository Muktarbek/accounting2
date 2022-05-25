package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    @Query("select i from InvoiceEntity i where (i.client.client_id =: clientId or :clientId is null)" +
            "and(i.dateOfCreation between :startDate and :endDate)" +
            "and upper(i.status) like concat ('%',:status,'%')" +
            "and(i.id = :invoiceNumber or :invoiceNumber is null)" +
            "and i.client.income = :isIncome")
    Page<InvoiceEntity> findAllByPagination(Long clientId, @Param("status") String status, LocalDateTime startDate, LocalDateTime endDate, Long invoiceNumber, Pageable pageable, Boolean isIncome);

    @Query("select i from InvoiceEntity i where i.status = :status and i.endDate < :date")
    List<InvoiceEntity> getAllByStatusAndDate(InvoiceStatus status, LocalDateTime date);

    @Query("select i from InvoiceEntity i where (i.client.client_id =: clientId or :clientId is null)" +
            "and(i.dateOfCreation between :startDate and :endDate)" +
            "and upper(i.status) like concat ('%',:status,'%')" +
            "and(i.id = :invoiceNumber or :invoiceNumber is null)" +
            "and i.client.income = :isIncome")
    Page<InvoiceEntity> findAll(Long clientId, @Param("status") String status, LocalDateTime startDate, LocalDateTime endDate, Long invoiceNumber, Pageable pageable, Boolean isIncome);


    @Query("select i from InvoiceEntity i inner join i.payments p inner join i.products pr inner  join pr.category c " +
            "where (upper(c.title) LIKE CONCAT('%',:category,'%')) " +
            "and (p.typeOfPay = :typeOfPay)" +
            "and (i.dateOfCreation between :startDate and :endDate)" +
            "and i.client.income = :isIncome and i.status='PAID' or :isIncome is null and i.status='PAID'")
    Page<InvoiceEntity> findAllTransaction(LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           @Param("typeOfPay") TypeOfPay typeOfPay,
                                           @Param("category") String category,
                                           Pageable pageable,
                                           Boolean isIncome);
}
