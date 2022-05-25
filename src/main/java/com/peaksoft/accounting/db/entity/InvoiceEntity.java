package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "invoices")
@Setter
@Getter
//@NoArgsConstructor
@Builder
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_sequence")
    @SequenceGenerator(name = "invoice_sequence", sequenceName = "invoice_seq", allocationSize = 1)
    private Long id;
    private String title;
    private LocalDateTime dateOfCreation = LocalDateTime.now();
    @JsonIgnore
    @ManyToOne(targetEntity = ClientEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.NOT_PAID;
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    @ManyToMany(targetEntity = ProductEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "invoices_products",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<ProductEntity> products;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "invoice")
    private List<PaymentEntity> payments;
    private Double sum;

    public void addClient(ClientEntity client) {
        this.client = client;
        client.addInvoice(this);
    }

    public void addProduct(ProductEntity product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        product.addInvoice(this);
    }

    public InvoiceEntity() {
    }

    public InvoiceEntity(Long id, Double sum) {
        this.id = id;
        this.sum = sum;
    }

    public InvoiceEntity(Long id, String title, LocalDateTime dateOfCreation, ClientEntity client, LocalDateTime startDate, LocalDateTime endDate, InvoiceStatus status, Collection<ProductEntity> products, Collection<PaymentEntity> payments, Double sum) {
        this.id = id;
        this.title = title;
        this.dateOfCreation = dateOfCreation;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.products = (List<ProductEntity>) products;
        this.payments = (List<PaymentEntity>) payments;
        this.sum = sum;
    }
}
