package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
