package com.sanchez.mapper;

import com.sanchez.model.*;
import com.sanchez.openapi.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.function.Function;

public class MapperUtil {

    private final static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static Function<Account, AccountResponse> accountToAccountResponse() {
        return source -> mapper.map(source, AccountResponse.class);
    }

    public static Function<Card, CardResponse> cardToCardResponse() {
        return source -> mapper.map(source, CardResponse.class);
    }

    public static Function<AccountTransaction, AccountTransactionResponse> accountTransactionToAccountTransactionResponse() {
        return source -> mapper.map(source, AccountTransactionResponse.class);
    }

    public static Function<CardTransaction, CardTransactionResponse> cardTransactionToCardTransactionResponse() {
        return source -> mapper.map(source, CardTransactionResponse.class);
    }

    public static Function<Atm, AtmResponse> atmToAtmResponse() {
        return source -> mapper.map(source, AtmResponse.class);
    }
}
