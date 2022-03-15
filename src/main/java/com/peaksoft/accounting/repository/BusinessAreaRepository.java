package com.peaksoft.accounting.repository;

import com.peaksoft.accounting.model.BusinessAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAreaRepository extends JpaRepository<BusinessAreaEntity,Long> {
}
