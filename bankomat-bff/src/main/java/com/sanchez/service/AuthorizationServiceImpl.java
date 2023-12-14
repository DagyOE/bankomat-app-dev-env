package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import com.sanchez.model.Account;
import com.sanchez.model.Card;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl {

    private final CardServiceImpl cardService;
    private final AccountServiceImpl accountService;

    public void cardCredentialsCheck(DelegateExecution execution) {

        String cardNumber = (String) execution.getVariable(Variable.CARD_NUMBER.getKey());
        String pin = (String) execution.getVariable(Variable.PIN.getKey());

        log.info("String Card credentials checking");

        Card card = cardService.getCardByCardNumber(cardNumber);

        if (!card.getPin().equals(pin)) {
            log.info("Card credentials checking, has been failed");
            execution.setVariable(Variable.ERROR_MESSAGE.getKey(), "Invalid card pin");
            throw new BpmnError("InvalidCardPin");
        }

        log.info("Card credentials checking, has been completed");
    }

    @Transactional
    public void generateNewToken(DelegateExecution execution) {

        String cardNumber = (String) execution.getVariable(Variable.CARD_NUMBER.getKey());

        log.info("Start generating new token for user");

        Account account = accountService.getAccountByCardInformation(cardNumber);
        account.setToken(UUID.randomUUID().toString());

        execution.setVariable(Variable.ACCOUNT_ID.getKey(), account.getId());
        execution.setVariable(Variable.TOKEN.getKey(), account.getToken());

        log.info("Generating new token for user, has been completed");
    }
}
