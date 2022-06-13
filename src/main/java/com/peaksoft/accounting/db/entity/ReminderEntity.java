package com.peaksoft.accounting.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "reminder_id_seq")
    @SequenceGenerator(name = "reminder_id_seq", sequenceName = "reminder_id_seq", allocationSize = 1)
    private Long id;
    private LocalDateTime dateOfPayment;
    private int day;
}
