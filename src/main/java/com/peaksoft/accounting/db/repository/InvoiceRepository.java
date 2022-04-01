package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.InvoiceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {
    @Query("select i from InvoiceEntity i")
    List<InvoiceEntity> findAllByPagination(Pageable pageable);
}
