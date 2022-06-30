package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companies_seq")
    @SequenceGenerator(name = "companies_gen", sequenceName = "companies_seq", allocationSize = 1)
    private Long company_id;
    private String companyName;

    @JsonIgnore
    @OneToMany(mappedBy = "companyName")
    private List<UserEntity> user;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<BankAccountEntity> bankAccounts;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<CategoryEntity> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<InvoiceEntity> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<PaymentEntity> payments;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<ProductEntity> products;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<TagEntity> tags;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<ClientEntity> clients;
}
