package com.peaksoft.accounting.api.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/myaccount/auth")
public class ForgotPasswordController {
    @PreAuthorize("hasAuthority('MY_ACCOUNT_ADMIN')")
    @GetMapping("/forgot-password")
    public String forgotPassword(@Param("code")String code, Model model){
        model.addAttribute("code",code);
        return "redirect::reset_password";
    }
}
