package com.peaksoft.accounting.api.controller;

import com.peaksoft.accounting.api.payload.*;
import com.peaksoft.accounting.config.jwt.JwtTokenUtil;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.api.payload.LoginMapper;
import com.peaksoft.accounting.db.repository.UserRepository;
import com.peaksoft.accounting.service.UserService;
import com.peaksoft.accounting.service.UserServiceImpl;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myaccount/auth")
@CrossOrigin
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginMapper loginMapper;
    private final UserRepository userRepository;

    @PostMapping("login")
    @Operation(summary = "Request to get a token", description = "Token generation")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword());
            UserEntity user = userRepository.findByEmail(passwordAuthenticationToken.getName()).get();
            return ResponseEntity.ok()
                    .body(loginMapper.toLoginView(jwtTokenUtil.generateToken(user), ValidationExceptionType.SUCCESSFUL, user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMapper.toLoginView("", ValidationExceptionType.LOGIN_FAILED, null));
        }
    }

    @PostMapping("registration")
    public UserResponse create(@RequestBody @Valid UserRequest request,
                               UserEntity user) {
        return userService.create(user, request);
    }

    @PutMapping("reset-password")
    public ResponseEntity<UserResponse> resetPassword(@RequestBody PasswordRequest request){
            return new ResponseEntity<>(userService.resetPassword(request),HttpStatus.OK);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendMessage(@RequestBody ForgotPasswordRequest passwordRequest,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
     UserEntity user  = userRepository.findByEmail(passwordRequest.getEmail()).get();
            userService.insert(user,getSiteURL(request));
            return new ResponseEntity<>(HttpStatus.OK);
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
