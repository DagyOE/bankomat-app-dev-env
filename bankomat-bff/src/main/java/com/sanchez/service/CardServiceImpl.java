package com.sanchez.service;

import com.sanchez.model.Card;
import com.sanchez.model.CardTransaction;
import com.sanchez.openapi.model.CardTransactionResponse;
import com.sanchez.openapi.model.CardVerificationResponse;
import com.sanchez.repository.CardRepository;
import com.sanchez.repository.CardTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl {

    private final CardRepository cardRepository;
    private final CardTransactionRepository cardTransactionRepository;
    private final AtmServiceImpl atmService;

    public boolean expirationCheck(String expirationDate, String cardNumber) {
        log.info("Start checking expiration date for card: {}", cardNumber);

        Card card = getCardByCardNumber(cardNumber);

        if (!card.getExpirationDate().equals(expirationDate)) {
            return true;
        }

        log.info("Checking expiration date for card: {}, has been completed", cardNumber);
        return false;
    }

    @Transactional
    public void createCardTransaction(String cardNumber, String transactionId, String atmId, CardVerificationResponse.StatusEnum responseStatus) {
        log.info("Start Creating card transaction record for card: {}", cardNumber);

        CardTransaction cardTransaction = new CardTransaction();

        cardTransaction.setId(transactionId);
        cardTransaction.setCard(getCardByCardNumber(cardNumber));
        cardTransaction.setAtm(atmService.getAtmById(atmId));
        cardTransaction.setCreatedAt(OffsetDateTime.now());
        cardTransaction.setStatus(CardTransactionResponse.StatusEnum.fromValue(
                responseStatus.getValue()
        ));

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
