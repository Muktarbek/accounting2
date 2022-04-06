package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {
    @Query("select i from InvoiceEntity i where (i.client.client_id =: clientId or :clientId is null)" +
            "and(i.dateOfCreation between :startDate and :endDate)" +
            "and(i.status = :status or :status is null)" +
            "and(i.id = :invoiceNumber or :invoiceNumber is null)")
    Page<InvoiceEntity> findAllByPagination(Long clientId, String status, LocalDateTime startDate, LocalDateTime endDate, Long invoiceNumber, Pageable pageable);


}