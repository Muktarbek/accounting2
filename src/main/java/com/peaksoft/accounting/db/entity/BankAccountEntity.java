package com.peaksoft.accounting.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankAccountName;
    private String bankAccountNumber;
}
