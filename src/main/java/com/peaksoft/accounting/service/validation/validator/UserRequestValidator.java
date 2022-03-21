package com.peaksoft.accounting.service.validation.validator;

import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.repository.UserRepository;
import com.peaksoft.accounting.service.validation.exception.ValidationException;
import com.peaksoft.accounting.service.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestValidator {

    private final UserRepository repository;

    public void validate(UserEntity registeredUser, UserRequest request) {

        if (request == null || registeredUser == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ValidationException(ValidationExceptionType.EMAIL_ALREADY_EXISTS);
        }
        if (!request.getPassword().equals(request.getRepeat_password())) {
            throw new ValidationException(ValidationExceptionType.PASSWORDS_DONT_MATCH);
        }
    }
}