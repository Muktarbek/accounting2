package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    List<CategoryEntity> findAllByIsIncomeCategory(boolean flag);
}
