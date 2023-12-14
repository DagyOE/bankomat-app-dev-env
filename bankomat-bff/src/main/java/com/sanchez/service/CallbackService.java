package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import com.sanchez.openapi.model.*;
import com.sanchez.openapi.model.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackService {

    private final HistoryService historyService;

    public CallbackCardVerification cardVerificationCallback(String transactionId) {
        log.info("Start creating callback for Card Verification with transactionId: {}", transactionId);

        CallbackCardVerification callbackCardVerification = new CallbackCardVerification();

        HistoricProcessInstance processInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(transactionId)
                .singleResult();

        if (Objects.isNull(processInstance)) {
            return callbackCardVerification.error(new Error()
                    .message("Process do not exists"));
        }

        if (Objects.nonNull(historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName(Variable.ERROR_MESSAGE.getKey())
                .singleResult())) {
            return callbackCardVerification
                    .isCompleted(false)
                    .error(new Error()
                        .message(
                            (String) historyService
                            .createHistoricVariableInstanceQuery()
                            .processInstanceId(processInstance.getId())
                            .variableName(Variable.ERROR_MESSAGE.getKey())
                            .singleResult().getValue())
            );
        }

        callbackCardVerification
                .isCompleted(true)
                .setResponse(new CardVerificationResponse()
                    .accountId(
                        (String) historyService
                        .createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .variableName(Variable.ACCOUNT_ID.getKey())
                        .singleResult().getValue())
                    .token(
                        (String) historyService
                        .createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .variableName(Variable.TOKEN.getKey())
                        .singleResult().getValue())
        );

        log.info("Creating callback for Card Verification with transactionId: {}, has been completed", transactionId);
        return callbackCardVerification;
    }

    public CallbackWithdrawal withdrawalCallback(String transactionId) {
        log.info("Start creating callback for Card eject with transactionId: {}", transactionId);

        CallbackWithdrawal callbackCardEject = new CallbackWithdrawal();

        HistoricProcessInstance processInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(transactionId)
                .singleResult();

        if (Objects.isNull(processInstance)) {
            return callbackCardEject.error(new Error()
                    .message("Process do not exists"));
        }

        if (Objects.nonNull(historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName(Variable.ERROR_MESSAGE.getKey())
                .singleResult())) {
            return callbackCardEject
                    .isCompleted(false)
                    .error(new Error()
                            .message(
                                    (String) historyService
                                            .createHistoricVariableInstanceQuery()
                                            .processInstanceId(processInstance.getId())
                                            .variableName(Variable.ERROR_MESSAGE.getKey())
                                            .singleResult().getValue())
                    );
        }

        callbackCardEject
                .isCompleted(true)
                .setResponse(new WithdrawalResponse()
                        .message("Withdrawal from the ATM was successful")
                );

        log.info("Creating callback for Card eject with transactionId: {}, has been completed", transactionId);
        return callbackCardEject;
    }

    public CallbackCardEject cardEjectCallback(String transactionId) {
        log.info("Start creating callback for Card eject with transactionId: {}", transactionId);

        CallbackCardEject callbackCardEject = new CallbackCardEject();

        HistoricProcessInstance processInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(transactionId)
                .singleResult();

        if (Objects.isNull(processInstance)) {
            return callbackCardEject.error(new Error()
                    .message("Process do not exists"));
        }

        if (Objects.nonNull(historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName(Variable.ERROR_MESSAGE.getKey())
                .singleResult())) {
            return callbackCardEject
                    .isCompleted(false)
                    .error(new Error()
                            .message(
                                    (String) historyService
                                            .createHistoricVariableInstanceQuery()
                                            .processInstanceId(processInstance.getId())
                                            .variableName(Variable.ERROR_MESSAGE.getKey())
                                            .singleResult().getValue())
                    );
        }

        callbackCardEject
                .isCompleted(true)
                .setResponse(new CardEjectResponse()
                        .message("Card ejection from the ATM was successful")
                );

        log.info("Creating callback for Card eject with transactionId: {}, has been completed", transactionId);
        return callbackCardEject;
    }
}
