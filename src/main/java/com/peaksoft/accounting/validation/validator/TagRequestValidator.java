package com.peaksoft.accounting.validation.validator;

import com.peaksoft.accounting.api.payload.TagRequest;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.repository.TagRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagRequestValidator {

    private final TagRepository tagRepository;

    public void validate(TagEntity tag, TagRequest tagRequest){

        if (tag == null || tagRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        if (tagRepository.findByNameTag(tagRequest.getNameTag()).isPresent()){
            throw new ValidationException(ValidationExceptionType.TAG_ALREADY_EXISTS);
        }
    }
}
