package com.sanchez.enumerate;

public enum CamundaProcess {
    CARD_VERIFICATION("CardVerification"),
    WITHDRAWAL("Withdrawal"),
    CARD_EJECT("CardEject");

    private String key;

    CamundaProcess(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
