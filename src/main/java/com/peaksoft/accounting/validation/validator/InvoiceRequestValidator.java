package com.peaksoft.accounting.validation.validator;

import com.peaksoft.accounting.api.payload.InvoiceRequest;
import com.peaksoft.accounting.api.payload.TagRequest;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceRequestValidator {
    public void validate(InvoiceEntity invoice, InvoiceRequest invoiceRequest){
        if (invoice == null || invoiceRequest == null) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
    }
}
