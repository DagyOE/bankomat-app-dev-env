package com.sanchez.mapper;

import com.sanchez.model.*;
import com.sanchez.openapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mapper {

    public static List<AccountResponse> accountListToAccountResponseList(List<Account> accounts) {
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            AccountResponse accountResponse = MapperUtil.accountToAccountResponse().apply(account);

            if (Objects.nonNull(account.getAccountTransactions())) {
                accountResponse.setAccountTransactionResponseList(
                        accountTransactionResponsesMapper(account.getAccountTransactions())
                );
            }

            if (Objects.nonNull(account.getCards())) {
                accountResponse.setCardResponseList(
                        cardResponsesMapper(account.getCards())
                );
            }

            accountResponses.add(accountResponse);
        }
        return accountResponses;
    }

    public static List<AtmResponse> atmListToAtmResponseList(List<Atm> atms) {
        List<AtmResponse> atmResponses = new ArrayList<>();
        for (Atm atm : atms) {
            AtmResponse atmResponse = MapperUtil.atmToAtmResponse().apply(atm);

            if (Objects.nonNull(atm.getAccountTransactions())) {
                atmResponse.setAccountTransactionResponseList(
                        accountTransactionResponsesMapper(atm.getAccountTransactions())
                );
            }

            if (Objects.nonNull(atm.getCardTransactions())) {
                atmResponse.setCardTransactionResponseList(
                        cardTransactionResponsesMapper(atm.getCardTransactions())
                );
            }

            atmResponses.add(atmResponse);
        }
        return atmResponses;
    }

    public static List<CardResponse> cardResponsesMapper(List<Card> cards) {
        List<CardResponse> cardResponses = new ArrayList<>();
        for (Card card : cards) {
            CardResponse cardResponse = MapperUtil.cardToCardResponse().apply(card);

            if (Objects.nonNull(card.getCardTransactionList())) {
                cardResponse.setCardTransactionResponseList(
                        cardTransactionResponsesMapper(card.getCardTransactionList())
                );
            }

            cardResponses.add(cardResponse);
        }
        return cardResponses;
    }

    public static List<AccountTransactionResponse> accountTransactionResponsesMapper(List<AccountTransaction> accountResponses) {
        List<AccountTransactionResponse> accountTransactionResponses = new ArrayList<>();
        for (AccountTransaction accountTransaction : accountResponses) {
            accountTransactionResponses.add(MapperUtil.accountTransactionToAccountTransactionResponse().apply(accountTransaction));
        }
        return accountTransactionResponses;
    }

    public static List<CardTransactionResponse> cardTransactionResponsesMapper(List<CardTransaction> cardTransactions) {
        List<CardTransactionResponse> cardTransactionResponses = new ArrayList<>();
        for (CardTransaction cardTransaction : cardTransactions) {
            cardTransactionResponses.add(MapperUtil.cardTransactionToCardTransactionResponse().apply(cardTransaction));
        }
        return cardTransactionResponses;
    }
}
