package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query("select p from ProductEntity p where p.isIncome = :flag")
    Page<ProductEntity> findAllByPagination(Pageable pageable, boolean flag);

    @Query("select p from ProductEntity p where p.isIncome = :flag")
    List<ProductEntity> findAllByPagination(boolean flag);

    List<ProductEntity> findAllByIsIncome(boolean isIncome);

    @Query("select p from ProductEntity p")
    List<ProductEntity> findAllByPagination(Pageable pageable);
}
