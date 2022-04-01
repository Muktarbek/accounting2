package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.accounting.enums.InvoiceStatus;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @JsonIgnore
    @ManyToMany(targetEntity = ClientEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "invoices_clients",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")})
    private List<ClientEntity> clients;
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

    public void addClient(ClientEntity client){
        if(clients == null){
            clients = new ArrayList<>();
        }
        clients.add(client);
        client.addInvoice(this);
    }
    public void addProduct(ProductEntity product){
        if(products == null){
            products = new ArrayList<>();
        }
        products.add(product);
        product.addInvoice(this);
    }


}
