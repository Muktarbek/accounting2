package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.ReminderRequest;
import com.peaksoft.accounting.api.payload.ReminderResponse;
import com.peaksoft.accounting.db.entity.ProductEntity;
import com.peaksoft.accounting.db.entity.ReminderEntity;
import com.peaksoft.accounting.db.repository.ProductRepository;
import com.peaksoft.accounting.db.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final ProductRepository productRepository;

    public ReminderResponse createReminder(ReminderRequest reminderRequest){
        ProductEntity product = productRepository.getById(reminderRequest.getProductId());
        ReminderEntity reminder = reminderRepository.save(mapToEntity(reminderRequest));
        product.setReminder(reminder);
        return mapToResponse(reminder);
    }

    public ReminderResponse mapToResponse(ReminderEntity reminderEntity){
        return ReminderResponse.builder()
                .id(reminderEntity.getId())
                .day(reminderEntity.getDay())
                .localDate(reminderEntity.getDateOfPayment()).build();
    }

    public ReminderEntity mapToEntity(ReminderRequest reminderRequest){
        return ReminderEntity.builder()
                .dateOfPayment(reminderRequest.getLocalDate())
                .day(reminderRequest.getDay()).build();
    }

    public ReminderResponse deleteReminder(Long id){
        ReminderEntity reminder = reminderRepository.getById(id);
        reminderRepository.delete(reminder);
        return mapToResponse(reminder);
    }

}
