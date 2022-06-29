package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    @Query("select c from CategoryEntity c where c.isIncomeCategory=:flag and c.company.company_id=:companyId")
    List<CategoryEntity> findAllByIsIncomeCategory(Long companyId,boolean flag);
    @Query("select c from CategoryEntity c where c.company.company_id=:companyId")
    List<CategoryEntity> findAllByCompany(Long companyId);
}
