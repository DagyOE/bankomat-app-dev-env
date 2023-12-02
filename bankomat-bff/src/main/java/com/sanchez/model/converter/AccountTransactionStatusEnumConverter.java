package com.sanchez.model.converter;

import com.sanchez.openapi.model.AccountTransactionResponse;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountTransactionStatusEnumConverter implements AttributeConverter<AccountTransactionResponse.StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(AccountTransactionResponse.StatusEnum statusEnum) {
        if (statusEnum == null) {
            return null;
        }
        return statusEnum.getValue();
    }

    @Override
    public AccountTransactionResponse.StatusEnum convertToEntityAttribute(String s) {
        return s == null ? null : AccountTransactionResponse.StatusEnum.fromValue(s);
    }
}
