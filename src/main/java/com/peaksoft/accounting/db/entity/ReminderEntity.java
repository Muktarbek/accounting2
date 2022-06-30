package com.peaksoft.accounting.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "reminder_gen")
    @SequenceGenerator(name = "reminder_gen", sequenceName = "reminder_seq", allocationSize = 1)
    private Long id;
    private LocalDate dateOfPayment;
    private int day;
}
