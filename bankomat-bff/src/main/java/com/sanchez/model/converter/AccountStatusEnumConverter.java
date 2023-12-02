package com.sanchez.model.converter;

import com.sanchez.openapi.model.AccountResponse;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountStatusEnumConverter implements AttributeConverter<AccountResponse.StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(AccountResponse.StatusEnum statusEnum) {
        if (statusEnum == null) {
            return null;
        }
        return statusEnum.getValue();
    }

    @Override
    public AccountResponse.StatusEnum convertToEntityAttribute(String s) {
        return s == null ? null : AccountResponse.StatusEnum.fromValue(s);
    }
}
