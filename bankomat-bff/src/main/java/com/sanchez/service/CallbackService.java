package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import com.sanchez.openapi.model.CardVerificationResponse;
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

    public CardVerificationResponse cardVerificationCallback(String transactionId) {
        log.info("Start creating callback for Card Verification with transactionId: {}", transactionId);

        HistoricProcessInstance processInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(transactionId)
                .singleResult();

        if (Objects.isNull(processInstance)) {
            return null;
        }

        if (Objects.nonNull(historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName(Variable.ERROR_MESSAGE.getKey())
                .singleResult())) {
            return null;
        }

        CardVerificationResponse cardVerificationResponse = new CardVerificationResponse();

        cardVerificationResponse.setAccountId(
                (String) historyService
                        .createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .variableName(Variable.ACCOUNT_ID.getKey())
                        .singleResult().getValue()
        );

        cardVerificationResponse.setToken(
                (String) historyService
                        .createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .variableName(Variable.TOKEN.getKey())
                        .singleResult().getValue()
        );

        log.info("Creating callback for Card Verification with transactionId: {}, has been completed", transactionId);
        return cardVerificationResponse;
    }
}
