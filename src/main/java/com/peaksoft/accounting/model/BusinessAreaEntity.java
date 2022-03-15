package com.peaksoft.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "business_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "businessAreaSequences", sequenceName = "area_seq", allocationSize = 1)
    private Long businessAreaId;
    private String area;
}
