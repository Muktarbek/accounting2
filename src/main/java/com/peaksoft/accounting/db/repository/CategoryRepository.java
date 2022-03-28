package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

}
