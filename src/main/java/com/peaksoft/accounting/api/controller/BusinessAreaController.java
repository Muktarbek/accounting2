package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.BusinessAreaResponse;
import com.peaksoft.accounting.service.BusinessAreaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/myaccount/business-area")

public class BusinessAreaController {

    private final BusinessAreaService businessAreaService;

    @GetMapping()
    @Operation(summary = "Get all business areas", description = "Getting all existing areas ")
    public List<BusinessAreaResponse> getAllAreas(){
        return businessAreaService.getAllAreas();
    }
}
