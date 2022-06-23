package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.enums.TypeOfPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository
        extends JpaRepository<BankAccountEntity, Long> {

    Optional<BankAccountEntity> findByBankAccountName(String bankAccountName);
    @Query("select b from BankAccountEntity b where b.typeOfPay =:typeOfPay or :typeOfPay is null")
    List<BankAccountEntity> findAll(TypeOfPay typeOfPay);

}