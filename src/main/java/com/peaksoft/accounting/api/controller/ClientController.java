package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ClientRequest;
import com.peaksoft.accounting.api.payload.ClientResponse;
import com.peaksoft.accounting.api.payload.ClientResponseView;
import com.peaksoft.accounting.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/client")
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
public class
ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientResponse create(@RequestBody @Valid ClientRequest request) {
        return clientService.create(request);
    }

    @PutMapping("{id}")
    public ClientResponse update(@PathVariable long id, @RequestBody @Valid ClientRequest request) {
        return clientService.update(request, id);
    }

    @GetMapping("{id}")
    public ClientResponse findById(@PathVariable long id) {
        return clientService.findById(id);
    }

    @DeleteMapping("{id}")
    public ClientResponse deleteById(@PathVariable long id) {
        return clientService.deleteById(id);
    }

    @GetMapping
    public ClientResponseView getAllClients(@RequestParam(value = "name",required = false) String name,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size){
        return clientService.getAllClients(name,page, size);
    }
}
