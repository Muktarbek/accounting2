package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ReminderRequest;
import com.peaksoft.accounting.api.payload.ReminderResponse;
import com.peaksoft.accounting.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("/api/myaccount/reminder")
@RequiredArgsConstructor
@CrossOrigin
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping
    @Operation(summary = "Create reminder", description = "Creating reminder for product")
    public ReminderResponse createReminder(@RequestBody ReminderRequest reminderRequest) {
        return reminderService.createReminder(reminderRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete", description = "Delete reminder")
    public ReminderResponse deleteReminder(@PathVariable Long id){
        return reminderService.deleteReminder(id);
    }
}
