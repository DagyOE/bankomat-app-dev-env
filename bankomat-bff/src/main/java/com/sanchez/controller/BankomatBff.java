package com.sanchez.controller;

import com.sanchez.openapi.api.BankomatBffApiDelegate;
import com.sanchez.openapi.model.*;
import com.sanchez.service.AccountServiceImpl;
import com.sanchez.service.AtmServiceImpl;
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
    private final AccountServiceImpl accountService;
    private final AtmServiceImpl atmService;

    @Override
    public ResponseEntity<CardVerificationResponse> cardVerification(CardVerificationRequest cardVerificationRequest) {
        return new ResponseEntity<>(
                bankomatCamundaProcessService.cardVerification(cardVerificationRequest),
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<WithdrawalResponse> withdrawal(WithdrawalRequest withdrawalRequest) {
        return new ResponseEntity<>(
                bankomatCamundaProcessService.withdrawal(withdrawalRequest),
                HttpStatus.ACCEPTED);
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
}
