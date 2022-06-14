package com.peaksoft.accounting.db.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "business_area")
@Getter
@Setter
public class BusinessAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "business_area_seq")
    @SequenceGenerator(name = "business_area_gen", sequenceName = "business_area_seq", allocationSize = 1)
    private Long business_area_id;
    private String area;
}
