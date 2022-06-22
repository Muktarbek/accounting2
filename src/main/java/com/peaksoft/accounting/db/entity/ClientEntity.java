package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    @SequenceGenerator(name = "clients_gen", sequenceName = "clients_seq", allocationSize = 1)
    @Column(nullable = false)
    private Long client_id;
    private String companyName;
    private String clientName;
    private String sellerSurname;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isIncome = true;
    @CreatedDate
    private LocalDateTime created;

    @JsonIgnore
    @ManyToMany(targetEntity = TagEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "client_tag",
            joinColumns = {@JoinColumn(name = "clients_id")},
            inverseJoinColumns = {@JoinColumn(name = "tags_id")})
    private List<TagEntity> tags;

    @JsonIgnore
    @OneToMany(targetEntity = InvoiceEntity.class,
            mappedBy = "client", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<InvoiceEntity> invoices;

    public void addTags(TagEntity tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    public void removeTags(TagEntity tag) {
        if (tag.getTag_id().equals(tag.getTag_id())) {
            tags.remove(tag);
        }
    }

    public void addInvoice(InvoiceEntity invoice) {
        if (invoices == null) {
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
