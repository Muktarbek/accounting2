package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query("select p from ProductEntity p where p.isIncome = :flag and p.company.company_id=:companyId")
    Page<ProductEntity> findAllByPagination(Pageable pageable, boolean flag,Long companyId);

    @Query("select p from ProductEntity p where p.isIncome = :flag and p.company.company_id=:companyId")
    List<ProductEntity> findAllByPagination(boolean flag,Long companyId);

    @Query("select p from ProductEntity p where p.company.company_id=:companyId and p.isIncome=:isIncome")
    List<ProductEntity> findAllByIsIncome(boolean isIncome,Long companyId);

    @Query("select p from ProductEntity p where p.company.company_id=:companyId")
    List<ProductEntity> findAllByPagination(Pageable pageable,Long companyId);

    @Query("select p from ProductEntity p where p.title like concat(:title,'%') and p.isIncome=:flag and p.company.company_id=:companyId")
    List<ProductEntity> searchAllByTitle(String title,Boolean flag,Long companyId);

    @Query("select p from ProductEntity p where p.isIncome=:flag and p.company.company_id=:companyId")
    List<ProductEntity> getAll(Boolean flag,Long companyId);
}
