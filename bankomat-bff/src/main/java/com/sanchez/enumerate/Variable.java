package com.sanchez.enumerate;

public enum Variable {
    CARD_NUMBER("cardNumber"),
    EXPIRATION_DATE("expirationDate"),
    CVV("cvv"),
    PIN("pin"),
    TRANSACTION_ID("transactionId"),
    TOKEN("token"),
    ACCOUNT_ID("accountId"),
    AMOUNT("amount"),
    CURRENCY("currency"),
    ATM_ID("atmId"),
    ERROR_MESSAGE("errorMessage");

    private String key;

    Variable(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
