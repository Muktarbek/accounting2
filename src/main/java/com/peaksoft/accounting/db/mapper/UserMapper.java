package com.peaksoft.accounting.db.mapper;

import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.api.payload.UserResponse;
import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.repository.BusinessAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BusinessAreaRepository repository;

    public UserEntity create(UserRequest userRequest) {

        UserEntity user = new UserEntity();
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setBusinessName(userRequest.getBusinessName());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        BusinessAreaEntity businessArea = repository.findById(userRequest.getBusinessAreaId()).get();
        user.setBusinessArea(businessArea);
        return user;
    }

    public UserResponse viewUser(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        if (user.getId() != null) {
            userResponse.setId(String.valueOf(user.getId()));
        }

        userResponse.setEmail(user.getEmail());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setAddress(user.getAddress());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setBusinessName(user.getBusinessName());
        userResponse.setBusinessArea(user.getBusinessArea());
        userResponse.setDeleted(user.isDeleted());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setActive(user.isActive());
        userResponse.setCreated(LocalDateTime.now());
        return userResponse;
    }

}