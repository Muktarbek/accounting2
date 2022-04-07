package com.peaksoft.accounting.service;

import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.repository.InvoiceRepository;
import com.peaksoft.accounting.enums.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceTimeService {
    private final InvoiceRepository invoiceRepository;
    @Scheduled(cron = "0 1 1 * * *"  )
    public void scheduleFixedRateTaskAsync() {
        List<InvoiceEntity> invoices =invoiceRepository.getAllByStatusAndDate(InvoiceStatus.NOT_PAID, LocalDateTime.now());
        for (InvoiceEntity invoice : invoices) {
           invoice.setStatus(InvoiceStatus.EXPIRED);
           invoiceRepository.save(invoice);
        }
    }
}

