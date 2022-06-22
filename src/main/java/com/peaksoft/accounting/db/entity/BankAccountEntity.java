package com.peaksoft.accounting.db.entity;

import com.peaksoft.accounting.enums.TypeOfPay;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_account_gen", sequenceName = "bank_account_seq", allocationSize = 1)
    private Long id;
    private String bankAccountName;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeOfPay typeOfPay;
}
