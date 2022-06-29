package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private Long payment_id;
    private LocalDateTime paymentDate;
    private String paymentFile;

    @Enumerated(EnumType.STRING)
    private TypeOfPay typeOfPay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    @OneToOne(cascade = CascadeType.ALL)
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.NOT_PAID;
    private Boolean isIncomePayment = true;
    private Double amountOfMoney;
    private String comment;
    @CreatedDate
    private LocalDateTime created;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductEntity product;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private InvoiceEntity invoice;

    @OneToOne(cascade ={CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "company_name_id")
    private CompanyEntity company;
}
