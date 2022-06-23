package com.peaksoft.accounting.validation.validator;

import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.repository.CompanyRepository;
import com.peaksoft.accounting.db.repository.UserRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestValidator {

    private final CompanyRepository companyRepo;

    public void validate(UserEntity registeredUser, UserRequest request) {

        if (request == null || registeredUser == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new ValidationException(ValidationExceptionType.PASSWORDS_DONT_MATCH);
        }
        if (companyRepo.findByCompanyName(request.getCompanyName()).isPresent()){
            throw new ValidationException(ValidationExceptionType.COMPANY_ALREADY_EXISTS);
        }
    }
}