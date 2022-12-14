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
            "and i.company.company_id=:companyId " +
            "and upper(i.status) like concat ('%',:status,'%')" +
            "and(i.id =:invoiceNumber or :invoiceNumber is null)" +
            "and (i.isIncome =:isIncome) " +
            "and i.status !=:paid ")
    Page<InvoiceEntity> findAllByPagination(Long clientId, @Param("status") String status, LocalDateTime startDate, LocalDateTime endDate, Long invoiceNumber, Pageable pageable,InvoiceStatus paid, Boolean isIncome,Long companyId);

    @Query("select i from InvoiceEntity i where i.status = :status and i.endDate < :date ")
    List<InvoiceEntity> getAllByStatusAndDate(InvoiceStatus status, LocalDateTime date);

    @Query("select i from InvoiceEntity i where (i.client.client_id =:clientId or :clientId is null)" +
            "and i.company.company_id=:companyId " +
            "and(i.dateOfCreation between :startDate and :endDate)" +
            "and upper(i.status) like concat ('%',:status,'%')" +
            "and(i.id = :invoiceNumber or :invoiceNumber is null)" +
            "and i.isIncome = :isIncome")
    Page<InvoiceEntity> findAll(Long clientId, @Param("status") String status, LocalDateTime startDate, LocalDateTime endDate, Long invoiceNumber, Pageable pageable, Boolean isIncome,Long companyId);
//    @Query("select distinct i from InvoiceEntity i left join i.payments p  left   join i.products pr where " +
//            "(i.lastDateOfPayment between :startDate and :endDate) and " +
//            "(pr.isIncome =:status or :status is null) and" +
//            "(pr.category.id =:categoryId or :categoryId is null) and " +
//            "(p.typeOfPay =:typeOfPay or :typeOfPay is null ) and" +
//            "(i.status =:invoiceStatus)")
//    Page<InvoiceEntity> findAllTransaction(LocalDateTime startDate,LocalDateTime endDate,Boolean status,Long categoryId,InvoiceStatus invoiceStatus,TypeOfPay typeOfPay,Pageable pageable);

    @Query("select i from InvoiceEntity i where (i.dateOfCreation between :startDate and :endDate) and " +
            "i.company.company_id=:companyId and " +
            "(i.status =:status or :status is null) and " +
            "(i.client.client_id =:clientId or :clientId is null)")
    List<InvoiceEntity> getAllByClientId(Long clientId,LocalDateTime startDate,LocalDateTime endDate,InvoiceStatus status,Long companyId);

    @Query("select i from InvoiceEntity i where i.client.client_id =:clientId and i.status =:status and i.company.company_id=:companyId ")
    List<InvoiceEntity> getAllByClientAndStatus(Long clientId,InvoiceStatus status,Long companyId);
    @Query("select sum(i.restAmount) from InvoiceEntity i where (i.dateOfCreation between :startDate and :endDate) and " +
            "i.status =:status and i.isIncome=:flag and " +
            "i.company.company_id=:companyId")
    Double getSumDays(LocalDateTime startDate,LocalDateTime endDate,InvoiceStatus status,Boolean flag,Long companyId);

}
