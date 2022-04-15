package com.peaksoft.accounting.db.entity;

import com.peaksoft.accounting.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "service_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_sequence")
    @SequenceGenerator(name = "service_sequence", sequenceName = "service_seq", allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

}
