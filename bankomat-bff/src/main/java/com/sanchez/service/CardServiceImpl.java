package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import com.sanchez.model.Card;
import com.sanchez.model.CardTransaction;
import com.sanchez.openapi.model.CardTransactionResponse;
import com.sanchez.openapi.model.CardVerificationResponse;
import com.sanchez.repository.CardRepository;
import com.sanchez.repository.CardTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl {

    private final CardRepository cardRepository;
    private final CardTransactionRepository cardTransactionRepository;
    private final AtmServiceImpl atmService;
    private final RuntimeService runtimeService;

    public void checkCardDataValidity(DelegateExecution execution) {

        String cardNumber = (String) execution.getVariable(Variable.CARD_NUMBER.getKey());
        String expirationDate = (String) execution.getVariable(Variable.EXPIRATION_DATE.getKey());
        String cvv = (String) execution.getVariable(Variable.CVV.getKey());

        log.info("Start checking expiration date for card: {}", cardNumber);

        Card card = getCardByCardNumber(cardNumber);

        if (Objects.isNull(card) || !card.getCardNumber().equals(cardNumber) ||
                !card.getExpirationDate().equals(expirationDate) || !card.getCvv().equals(cvv)) {
            log.info("Checking expiration date for card: {}, has been failed", cardNumber);
            execution.setVariable(Variable.ERROR_MESSAGE.getKey(), "Invalid card data");
            throw new BpmnError("InvalidCardData");
        }

        log.info("Checking expiration date for card: {}, has been completed", cardNumber);
    }

    @Transactional
    public void createCardTransaction(DelegateExecution execution) {

        String cardNumber = (String) execution.getVariable(Variable.CARD_NUMBER.getKey());
        String transactionId = (String) execution.getVariable(Variable.TRANSACTION_ID.getKey());
        String atmId = (String) execution.getVariable(Variable.ATM_ID.getKey());
        String errorMessage = (String) execution.getVariable(Variable.ERROR_MESSAGE.getKey());

        log.info("Start Creating card transaction record for card: {}", cardNumber);

        CardTransaction cardTransaction = new CardTransaction();

        cardTransaction.setId(transactionId);
        cardTransaction.setCard(getCardByCardNumber(cardNumber)); //TODO: Problem with unexisting card
        cardTransaction.setAtm(atmService.getAtmById(atmId));
        cardTransaction.setCreatedAt(OffsetDateTime.now());
        if (StringUtils.hasLength(errorMessage)) {
            cardTransaction.setStatus(CardTransactionResponse.StatusEnum.CANCELLED);
        } else {
            cardTransaction.setStatus(CardTransactionResponse.StatusEnum.SUCCESS);
        }

        cardTransactionRepository.save(cardTransaction);

        log.info("Creating card transaction record for card: {}, has been completed", cardNumber);
    }

    public Card getCardByCardNumber(String cardNumber) {
        log.info("Start getting card from DB by cardNumber: {}", cardNumber);

        Card card = cardRepository.getCardByCardNumber(cardNumber);

        log.info("Getting card from DB by cardNumber: {}, has been completed", cardNumber);
        return card;
    }
}
