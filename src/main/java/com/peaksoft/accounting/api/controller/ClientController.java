package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ClientRequest;
import com.peaksoft.accounting.api.payload.ClientResponse;
import com.peaksoft.accounting.api.payload.ClientResponseView;
import com.peaksoft.accounting.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/clients")
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
public class
ClientController {

    private final ClientService clientService;

    @PostMapping
    @Operation(summary = "Create client", description = "Creating a new client")
    public ClientResponse create(@RequestBody @Valid ClientRequest request) {
        return clientService.create(request);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update client", description = "Update a new client by \"id\" in application ")
    public ClientResponse update(@PathVariable long id, @RequestBody @Valid ClientRequest request) {
        return clientService.update(request, id);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get client", description = "Getting an existing client by \"id\" in application ")
    public ClientResponse findById(@PathVariable long id) {
        return clientService.findById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete client", description = "delete an existing client by \"id\" in application ")
    public ClientResponse deleteById(@PathVariable long id) {
        return clientService.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Get all Clients", description = "Getting all existing clients and search ")
    public ClientResponseView getAllClients(@RequestParam(value = "name",required = false) String name,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size){
        return clientService.getAllClients(name,page, size);
    }
}
