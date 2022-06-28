package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.PasswordRequest;
import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.api.payload.UserResponse;
import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.repository.BusinessAreaRepository;
import com.peaksoft.accounting.db.repository.RoleRepository;
import com.peaksoft.accounting.db.repository.UserRepository;
import com.peaksoft.accounting.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserRequestValidator userRequestValidator;
    @Autowired
    private  BusinessAreaRepository businessRepository;
    @Autowired
    private JavaMailSender mailSender;
    private final RoleRepository roleRepository;

    public UserResponse create(UserEntity registeredUser, UserRequest request){
        userRequestValidator.validate(registeredUser,request);
        UserEntity user = mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return mapToResponse(user);
    }

    public String validate(String email){
        UserEntity user = userRepository.checkByEmail(email);
        return (user == null) ? "Unique" : "Duplicate";
    }

    public UserEntity mapToEntity(UserRequest userRequest) {
        UserEntity user = new UserEntity();
        CompanyEntity company = new CompanyEntity();
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        BusinessAreaEntity businessArea = businessRepository.findById(userRequest.getBusinessAreaId()).get();
        user.setBusinessArea(businessArea);
        company.setCompanyName(userRequest.getCompanyName());
        user.setCompanyName(company);
        user.addRole(roleRepository.getById(1L));
        return user;
    }

    public UserResponse mapToResponse(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        if (user.getUser_id() != null) {
            userResponse.setId(String.valueOf(user.getUser_id()));
        }
        userResponse.setEmail(user.getEmail());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setAddress(user.getAddress());
        userResponse.setCompanyName(user.getCompanyName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setBusinessArea(user.getBusinessArea());
        userResponse.setDeleted(user.isDeleted());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setActive(user.isActive());
        userResponse.setCreated(LocalDateTime.now());
        return userResponse;
    }

    public UserResponse resetPassword(PasswordRequest request){
        UserEntity user =  userRepository.findByToken(request.getToken())
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with token - %s, not found", request.getToken()))
                );
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setToken(null);
        UserEntity userEntity = userRepository.save(user);
        return mapToResponse(userEntity);
    }

    public void insert(UserEntity user,String siteUrl) throws UnsupportedEncodingException, MessagingException {
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        sendVerificationEmail(user,siteUrl);
    }

    public void sendVerificationEmail(UserEntity user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "peaksoft.us";
        String senderName = "Peaksoft.kg";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your forgot password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Verify</a></h3>"
                + "Thank you,<br>"
                + "Peaksoft.kg<br>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/api/myaccount/auth/forgot-password?code=" + user.getToken();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }

}
