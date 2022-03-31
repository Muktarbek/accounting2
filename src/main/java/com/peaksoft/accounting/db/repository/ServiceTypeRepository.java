package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity,Long> {
}
