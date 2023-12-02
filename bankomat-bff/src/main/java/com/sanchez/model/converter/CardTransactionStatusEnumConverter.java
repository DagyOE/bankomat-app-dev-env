package com.sanchez.model.converter;

import com.sanchez.openapi.model.CardTransactionResponse;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CardTransactionStatusEnumConverter implements AttributeConverter<CardTransactionResponse.StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(CardTransactionResponse.StatusEnum statusEnum) {
        if (statusEnum == null) {
            return null;
        }
        return statusEnum.getValue();
    }

    @Override
    public CardTransactionResponse.StatusEnum convertToEntityAttribute(String s) {
        return s == null ? null : CardTransactionResponse.StatusEnum.fromValue(s);
    }
}
