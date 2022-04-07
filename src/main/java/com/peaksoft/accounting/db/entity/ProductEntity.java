package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_id;
    private String title;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "service_type_id")
    @JsonIgnore
    private ServiceTypeEntity serviceType;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryEntity category;
    private String description;
    @ManyToMany(targetEntity = InvoiceEntity.class,
            mappedBy = "products", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<InvoiceEntity> invoices;

    public void addInvoice(InvoiceEntity invoice) {
        if(invoices == null){
            invoices = new ArrayList<>();
        }
        invoices.add(invoice);
    }
}
