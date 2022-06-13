package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {
}
