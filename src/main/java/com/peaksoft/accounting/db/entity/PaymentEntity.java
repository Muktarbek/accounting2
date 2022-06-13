package com.peaksoft.accounting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_payment_id_seq")
    @SequenceGenerator(name = "payment_payment_id_seq", sequenceName = "payment_payment_id_seq", allocationSize = 1)
    private Long payment_id;
    private LocalDateTime paymentDate;
    private String paymentFile;
    @Enumerated(EnumType.STRING)
    private TypeOfPay typeOfPay;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    private Double amountOfMoney;
    private String comment;
    @CreatedDate
    private LocalDateTime created;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.MERGE})
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private InvoiceEntity invoice;

}
