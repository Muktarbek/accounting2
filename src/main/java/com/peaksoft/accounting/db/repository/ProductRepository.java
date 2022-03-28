package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("select p from ProductEntity p")
    List<ProductEntity> findAllByPagination(Pageable pageable);
}
