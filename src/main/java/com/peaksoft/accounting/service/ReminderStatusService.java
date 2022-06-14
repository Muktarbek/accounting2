package com.peaksoft.accounting.service;

import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.enums.ReminderType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderStatusService {
    private final ProductRepository productRepository;

    @Scheduled(cron = "0 1 1 * * *")
    public void reminderStatus() {
        List<ProductEntity> products = productRepository.findAllByIsIncome(false);
        for (ProductEntity product : products) {

           LocalDate minusDays = LocalDate.now().minusDays(product.getReminder().getDay());
           LocalDate now = LocalDate.now();
           LocalDate dateOfPayment = product.getReminder().getDateOfPayment();

           if((minusDays.isAfter(now)|| minusDays.isEqual(now))
                   &&(dateOfPayment.isBefore(now)||dateOfPayment.isEqual(now))){
               product.setReminderType(ReminderType.PAY_FOR);
               productRepository.save(product);
           }

            if (dateOfPayment.isAfter(now)) {
                product.setReminderType(ReminderType.EXPIRED);
                productRepository.save(product);
            }

        }
    }
}
