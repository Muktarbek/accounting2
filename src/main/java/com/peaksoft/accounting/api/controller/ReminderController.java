package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ReminderRequest;
import com.peaksoft.accounting.api.payload.ReminderResponse;
import com.peaksoft.accounting.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/myaccount/reminder")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@CrossOrigin
@Slf4j
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
