package com.peaksoft.accounting.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private Boolean isIncomeCategory;
}
