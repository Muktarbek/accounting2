package com.peaksoft.accounting.db.entity;

import lombok.*;

import javax.persistence.*;

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
}
