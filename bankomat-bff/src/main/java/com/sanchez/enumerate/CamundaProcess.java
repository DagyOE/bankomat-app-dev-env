package com.sanchez.enumerate;

public enum CamundaProcess {
    CARD_VERIFICATION("CardVerification"),
    WITHDRAWAL("Withdrawal");

    private String key;

    CamundaProcess(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
