package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.TagRequest;
import com.peaksoft.accounting.api.payload.TagResponse;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
@RequestMapping("/api/myaccount/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public TagResponse create(@RequestBody @Valid TagRequest tagRequest, TagEntity tag) {
        return tagService.create(tag,tagRequest);
    }

    @GetMapping
    public List<TagResponse> getAllTags(){
        return tagService.getAllTags();
    }

    @PutMapping("{id}")
    public TagResponse update(@PathVariable long id, @RequestBody @Valid TagRequest tagRequest){
        return tagService.update(tagRequest,id);
    }

    @DeleteMapping("{id}")
    public TagResponse deleteById(@PathVariable long id){
        return tagService.deleteById(id);
    }

    @GetMapping("{id}")
    public TagResponse findById(@PathVariable long id){
        return tagService.findById(id);
    }

}
