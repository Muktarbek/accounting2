package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.api.payload.UserResponse;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.service.UserService;
import com.peaksoft.accounting.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/editors")
@CrossOrigin
@PreAuthorize("hasAnyAuthority('MY_ACCOUNT_ADMIN','MY_ACCOUNT_EDITOR')")
public class EditorController {

    private final UserService userService;

    @PostMapping("/create/editor")
    public UserResponse createEditor(@AuthenticationPrincipal UserEntity user, @RequestBody UserRequest request){
        user.getCompanyName().getCompany_id();
        return userService.createEditor(user.getCompanyName().getCompany_id(),request);
    }

    @GetMapping()
    public List<UserResponse> getAll(@AuthenticationPrincipal UserEntity user){
       return userService.getAllByCompany(user.getCompanyName().getCompany_id());
    }
    @GetMapping("{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getById(id);
    }
    @DeleteMapping("{id}")
    public UserResponse deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @PutMapping()
    public UserResponse update(@AuthenticationPrincipal UserEntity user,@RequestBody UserRequest request){
        return userService.update(user.getUser_id(),request);
    }


}
