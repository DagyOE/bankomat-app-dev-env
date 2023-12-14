package com.sanchez.service;

import com.sanchez.enumerate.CamundaProcess;
import com.sanchez.enumerate.Variable;
import com.sanchez.openapi.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CamundaProcessService {

    private final RuntimeService runtimeService;

    public void cardVerification(CardVerificationRequest cardVerificationRequest) {
        log.info("Starting card verification process with transactionId: {}", cardVerificationRequest.getTransactionId());

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceByKey(CamundaProcess.CARD_VERIFICATION.getKey())
                .businessKey(cardVerificationRequest.getTransactionId())
                .setVariable(Variable.CARD_NUMBER.getKey(), cardVerificationRequest.getCardNumber())
                .setVariable(Variable.EXPIRATION_DATE.getKey(), cardVerificationRequest.getExpirationDate())
                .setVariable(Variable.CVV.getKey(), cardVerificationRequest.getCvv())
                .setVariable(Variable.PIN.getKey(), cardVerificationRequest.getPin())
                .setVariable(Variable.TRANSACTION_ID.getKey(), cardVerificationRequest.getTransactionId())
                .setVariable(Variable.ATM_ID.getKey(), cardVerificationRequest.getAtmId())
                .execute();

        ProcessInstanceDto response = ProcessInstanceDto.fromProcessInstance(processInstance);
        log.info("Card verification process: {}", response);
    }

    public void withdrawal(WithdrawalRequest withdrawalRequest) {
        log.info("Starting withdrawal process with transactionId: {}", withdrawalRequest.getTransactionId());

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceByKey(CamundaProcess.WITHDRAWAL.getKey())
                .businessKey(withdrawalRequest.getTransactionId())
                .setVariable(Variable.ACCOUNT_ID.getKey(), withdrawalRequest.getAccountId())
                .setVariable(Variable.ATM_ID.getKey(), withdrawalRequest.getAtmId())
                .setVariable(Variable.TOKEN.getKey(), withdrawalRequest.getToken())
                .setVariable(Variable.AMOUNT.getKey(), withdrawalRequest.getAmount())
                .setVariable(Variable.CURRENCY.getKey(), withdrawalRequest.getCurrency())
                .setVariable(Variable.TRANSACTION_ID.getKey(), withdrawalRequest.getTransactionId())
                .execute();

        ProcessInstanceDto response = ProcessInstanceDto.fromProcessInstance(processInstance);
        log.info("Withdrawal process: {}", response);
    }

    public void cardEject(CardEjectRequest cardEjectRequest) {
        log.info("Starting withdrawal process with transactionId: {}", cardEjectRequest.getTransactionId());

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceByKey(CamundaProcess.WITHDRAWAL.getKey())
                .businessKey(cardEjectRequest.getTransactionId())
                .setVariable(Variable.ACCOUNT_ID.getKey(), cardEjectRequest.getAccountId())
                .setVariable(Variable.TRANSACTION_ID.getKey(), cardEjectRequest.getTransactionId())
                .execute();

        ProcessInstanceDto response = ProcessInstanceDto.fromProcessInstance(processInstance);
        log.info("Withdrawal process: {}", response);
    }
}
