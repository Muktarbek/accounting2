package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.accounting.enums.ReminderType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
    @SequenceGenerator(name = "products_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "service_type_id")
    @JsonIgnore
    private ServiceTypeEntity serviceType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryEntity category;

    private String description;
    private Boolean isIncome = true;

    @ManyToMany(targetEntity = InvoiceEntity.class,
            mappedBy = "products", cascade = {CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.DETACH},
            fetch = FetchType.EAGER)
    private List<InvoiceEntity> invoices;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reminderId")
    private ReminderEntity reminder;

    private ReminderType reminderType;

    public void addInvoice(InvoiceEntity invoice) {
        if (invoices == null) {
            invoices = new ArrayList<>();
        }
        invoices.add(invoice);
    }
}
