package com.sanchez.service;

import com.sanchez.enumerate.CamundaProcess;
import com.sanchez.enumerate.Variable;
import com.sanchez.openapi.model.CardVerificationRequest;
import com.sanchez.openapi.model.CardVerificationResponse;
import com.sanchez.openapi.model.WithdrawalRequest;
import com.sanchez.openapi.model.WithdrawalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CamundaProcessService {

    private final RuntimeService runtimeService;

    public CardVerificationResponse cardVerification(CardVerificationRequest cardVerificationRequest) {
        log.info("Starting card verification process with transactionId: {}", cardVerificationRequest.getTransactionId());

        ProcessInstanceWithVariables processInstance = runtimeService
                .createProcessInstanceByKey(CamundaProcess.CARD_VERIFICATION.getKey())
                .businessKey(cardVerificationRequest.getTransactionId())
                .setVariable(Variable.CARD_NUMBER.getKey(), cardVerificationRequest.getCardNumber())
                .setVariable(Variable.EXPIRATION_DATE.getKey(), cardVerificationRequest.getExpirationDate())
                .setVariable(Variable.CVV.getKey(), cardVerificationRequest.getCvv())
                .setVariable(Variable.PIN.getKey(), cardVerificationRequest.getPin())
                .setVariable(Variable.TRANSACTION_ID.getKey(), cardVerificationRequest.getTransactionId())
                .setVariable(Variable.ATM_ID.getKey(), cardVerificationRequest.getAtmId())
                .setVariable(Variable.ACCOUNT_RESPONSE.getKey(), null)
                .setVariable(Variable.RESPONSE_MESSAGE.getKey(), null)
                .executeWithVariablesInReturn();

        ProcessInstanceDto response = ProcessInstanceDto.fromProcessInstance(processInstance);
        log.info("Card verification process: {}", response);

        return processInstance.getVariables().getValue("buildedResponseMessage", CardVerificationResponse.class);
    }

    public WithdrawalResponse withdrawal(WithdrawalRequest withdrawalRequest) {
        log.info("Starting withdrawal process with transactionId: {}", withdrawalRequest.getTransactionId());

        ProcessInstanceWithVariables processInstance = runtimeService
                .createProcessInstanceByKey(CamundaProcess.WITHDRAWAL.getKey())
                .businessKey(withdrawalRequest.getTransactionId())
                .setVariable(Variable.ACCOUNT_ID.getKey(), withdrawalRequest.getAccountId())
                .setVariable(Variable.ATM_ID.getKey(), withdrawalRequest.getAtmId())
                .setVariable(Variable.TOKEN.getKey(), withdrawalRequest.getToken())
                .setVariable(Variable.AMOUNT.getKey(), withdrawalRequest.getAmount())
                .setVariable(Variable.CURRENCY.getKey(), withdrawalRequest.getCurrency())
                .setVariable(Variable.TRANSACTION_ID.getKey(), withdrawalRequest.getTransactionId())
                .setVariable(Variable.ACCOUNT_RESPONSE.getKey(), null)
                .setVariable(Variable.RESPONSE_MESSAGE.getKey(), null)
                .executeWithVariablesInReturn();

        ProcessInstanceDto response = ProcessInstanceDto.fromProcessInstance(processInstance);
        log.info("Withdrawal process: {}", response);

        return processInstance.getVariables().getValue("buildedResponseMessage", WithdrawalResponse.class);
    }
}
