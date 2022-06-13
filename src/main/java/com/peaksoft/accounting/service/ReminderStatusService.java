package com.peaksoft.accounting.service;

import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.enums.ReminderType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderStatusService {
    private final ProductRepository productRepository;

    @Scheduled(cron = "0 1 1 * * *")
    public void reminderStatus() {
        List<ProductEntity> products = productRepository.findAll();
        for (ProductEntity product : products) {
            if (product.getReminder().getDateOfPayment()
                    .minusDays(product.getReminder().getDay()).isEqual(LocalDateTime.now())) {
                product.setReminderType(ReminderType.PAY_FOR);
                productRepository.save(product);
            }
            if (product.getReminder().getDateOfPayment().isBefore(LocalDateTime.now())) {
                product.setReminderType(ReminderType.EXPIRED);
                productRepository.save(product);
            }
        }
    }
}
