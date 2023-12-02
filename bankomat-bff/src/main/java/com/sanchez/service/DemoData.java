package com.sanchez.service;

import com.sanchez.model.Account;
import com.sanchez.model.Atm;
import com.sanchez.model.Card;
import com.sanchez.openapi.model.AccountResponse;
import com.sanchez.repository.AccountRepository;
import com.sanchez.repository.AtmRepository;
import com.sanchez.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DemoData {

    private final AtmRepository atmRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void createDemoData() {

        if (atmRepository.findAll().isEmpty()) {

            Atm atm = new Atm();
            atm.setId(generateId());
            atm.setBalance(1000000F);

            atmRepository.save(atm);

            Account account = new Account();
            account.setId(generateId());
            account.setBalance(2000000F);
            account.setStatus(AccountResponse.StatusEnum.ACTIVATED);
            account.setCreatedAt(OffsetDateTime.now());

            accountRepository.save(account);

            Card card = new Card();
            card.setId(generateId());
            card.setCardNumber("1234567890123456");
            card.setExpirationDate("12/25");
            card.setCvv("123");
            card.setPin("9876");
            card.setCreatedAt(OffsetDateTime.now());

            card.setAccount(account);

            cardRepository.save(card);

        }

    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
