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

//    @Query("select new InvoiceEntity(" +
//            " i.id," +
//            " i.title," +
//            " i.dateOfCreation," +
//            " i.client," +
//            " i.startDate," +
//            " i.endDate," +
//            " i.status," +
//            " i.products , " +
//            " i.payments, " +
//            " i.sum) from InvoiceEntity i join i.products p " +
//            " where i.client.income = :isIncome or :isIncome is null and i.status = 'PAID'" +
//            " and i.dateOfCreation between :startDate and :endDate")
//    List<InvoiceEntity> findAllTransaction(@Param("startDate") LocalDateTime startDate,
//                                           @Param("endDate") LocalDateTime endDate,
//                                           Boolean isIncome);
//    select *
//    from invoices i
//    left join payment p on p.invoice_id = i.id
//    inner join invoices_products ip on ip.invoice_id = i.id
//    inner join products pro on ip.product_id = pro.id
//    inner join categories c on pro.category_id = c.id
//    where (? is null or c.title = ?)
//    and (? is null or p.payment_method = ?);

//        @Query("select i from InvoiceEntity i  inner  join i.payments p where (upper(p.typeOfPay) LIKE CONCAT('%',:typeOfPay,'%'))" +
//            "and(i.dateOfCreation between :startDate and :endDate)" +
//            "and i.client.income = :isIncome and i.status='PAID' or :isIncome is null and i.status='PAID'")
//    Page<InvoiceEntity> findAllTransaction(LocalDateTime startDate,
//                                           LocalDateTime endDate,
//                                           @Param("typeOfPay") String typeOfPay,
//                                           Pageable pageable,
//                                           Boolean isIncome);

//    @Query("select i from InvoiceEntity i inner join i.payments p inner join i.products pr inner  join pr.category c " +
//            "where (upper(c.title) LIKE CONCAT('%',:category,'%')) " +
//            "and (p.typeOfPay = :typeOfPay)" +
//            "and (i.dateOfCreation between :startDate and :endDate)" +
//            "and i.client.income = :isIncome and i.status='PAID' or :isIncome is null and i.status='PAID'")
//    Page<InvoiceEntity> findAllTransaction(LocalDateTime startDate,
//                                           LocalDateTime endDate,
//                                           @Param("typeOfPay") TypeOfPay typeOfPay,
//                                           @Param("category") String category,
//                                           Pageable pageable,
//                                           Boolean isIncome);

    @Query("select i from InvoiceEntity i inner join i.payments p  where (p.typeOfPay =:typeOfPay)" +
            "and(i.dateOfCreation between :startDate and :endDate)" +
            "and i.client.income = :isIncome and i.status='PAID' or :isIncome is null and i.status='PAID'")
    Page<InvoiceEntity> findAllTransaction(LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           TypeOfPay typeOfPay,
                                           Pageable pageable,
                                           Boolean isIncome);

}
