package com.peaksoft.accounting.db.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@RequiredArgsConstructor
@Getter
@Setter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long client_id;
    private String companyName;
    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive = true;
    @CreatedDate
    private LocalDateTime created;

    @ManyToMany(targetEntity = TagEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "client_tag",
            joinColumns = {@JoinColumn(name = "clients_id")},
            inverseJoinColumns = {@JoinColumn(name = "tags_id")})
    private List<TagEntity> tags;
    @ManyToMany(targetEntity = InvoiceEntity.class,
            mappedBy = "clients", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<InvoiceEntity> invoices;

    public void addInvoice(InvoiceEntity invoice){
        if(invoices == null) {
            invoices = new ArrayList<>();
        }
        invoices.add(invoice);
        }
    public Long getClient_id() {
        return client_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
