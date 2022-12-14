package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.ClientResponse;
import com.peaksoft.accounting.api.payload.SellerResponse;
import com.peaksoft.accounting.api.payload.TagRequest;
import com.peaksoft.accounting.api.payload.TagResponse;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
@RequestMapping("/api/myaccount/tags")
@CrossOrigin
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Create tag", description = "Creating a new tag")
    public TagResponse create(@AuthenticationPrincipal UserEntity user,
                              @RequestBody @Valid TagRequest tagRequest, TagEntity tag) {
        return tagService.create(tag,tagRequest,user.getCompanyName());
    }

    @GetMapping
    @Operation(summary = "Get all tags", description = "Getting all existing tags ")
    public List<TagResponse> getAllTags(@AuthenticationPrincipal UserEntity user){
        return tagService.getAllTags(user.getCompanyName().getCompany_id());
    }

    @PutMapping("{id}")
    @Operation(summary = "Update tag", description = "Update a new tag by \"id\" in application")
    public TagResponse update(@AuthenticationPrincipal UserEntity user,
                              @PathVariable long id,
                              @RequestBody @Valid TagRequest tagRequest){
        return tagService.update(tagRequest,id,user.getCompanyName());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete tag", description = "delete an existing payment by \"id\" in application ")
    public TagResponse deleteById(@PathVariable long id){
        return tagService.deleteById(id);
    }

    @GetMapping("{id}")
    @Operation(summary = "Getting tag", description = "Getting an existing payment by \"id\" in application")
    public TagResponse findById(@PathVariable long id){
        return tagService.findById(id);
    }

    @GetMapping("/search/by/name")
    public List<TagResponse> searchByName(@AuthenticationPrincipal UserEntity user,
                                          @RequestParam String tagName){
     return tagService.searchByName(tagName,user.getCompanyName().getCompany_id());
    }
    @GetMapping("/find/all")
    public List<TagResponse> findAll(@AuthenticationPrincipal UserEntity user){
        return tagService.getAll(user.getCompanyName().getCompany_id());
    }
}
