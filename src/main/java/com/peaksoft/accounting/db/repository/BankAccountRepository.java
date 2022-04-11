package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository
        extends JpaRepository<BankAccountEntity, Long> {

    Optional<BankAccountEntity> findByBankAccountName(String bankAccountName);

}