package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ReminderRequest;
import com.peaksoft.accounting.api.payload.ReminderResponse;
import com.peaksoft.accounting.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping
    public ReminderResponse createReminder(@RequestBody ReminderRequest reminderRequest) {
        return reminderService.createReminder(reminderRequest);
    }

    @DeleteMapping("{id}")
    public ReminderResponse deleteReminder(@PathVariable Long id){
        return reminderService.deleteReminder(id);
    }
}
