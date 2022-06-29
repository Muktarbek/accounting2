package com.peaksoft.accounting.db.entity;

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


    @OneToMany(mappedBy = "companyName")
    private List<UserEntity> user;

    @OneToMany(mappedBy = "company")
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "company")
    private List<InvoiceEntity> invoices;

    @OneToMany(mappedBy = "company")
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "company")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "company")
    private List<TagEntity> tags;

    @OneToMany(mappedBy = "company")
    private List<ClientEntity> clients;
}
