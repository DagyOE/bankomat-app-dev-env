package com.sanchez.controller;

import com.sanchez.openapi.api.BankomatBffApiDelegate;
import com.sanchez.openapi.model.*;
import com.sanchez.service.AccountServiceImpl;
import com.sanchez.service.AtmServiceImpl;
import com.sanchez.service.CallbackService;
import com.sanchez.service.CamundaProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankomatBff implements BankomatBffApiDelegate {

    private final CamundaProcessService bankomatCamundaProcessService;
    private final CallbackService callbackService;
    private final AccountServiceImpl accountService;
    private final AtmServiceImpl atmService;

    @Override
    public ResponseEntity<Void> cardVerification(CardVerificationRequest cardVerificationRequest) {
        bankomatCamundaProcessService.cardVerification(cardVerificationRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> withdrawal(WithdrawalRequest withdrawalRequest) {
        bankomatCamundaProcessService.withdrawal(withdrawalRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return new ResponseEntity<>(
                accountService.getAllAccounts(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AtmResponse>> getAllAtms() {
        return new ResponseEntity<>(
                atmService.getAllAtms(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CardVerificationResponse> cardVerificationCallback(String transactionId) {
        return new ResponseEntity<>(
                callbackService.cardVerificationCallback(transactionId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WithdrawalResponse> withdrawalCallback(String transactionId) {
        return BankomatBffApiDelegate.super.withdrawalCallback(transactionId);
    }
}
