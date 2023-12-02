package com.sanchez.service;

import com.sanchez.dtos.AccountResponseDto;
import com.sanchez.model.Account;
import com.sanchez.model.Card;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl {

    private final CardServiceImpl cardService;
    private final AccountServiceImpl accountService;

    @Transactional
    public AccountResponseDto cardCredentialsCheck(String cardNumber, String pin) throws Exception {
        log.info("String Card credentials checking");

        Card card = cardService.getCardByCardNumber(cardNumber);

        if (!card.getPin().equals(pin)) {
            return null;
        }

        Account account = accountService.getAccountByCardInformation(cardNumber);
        account.setToken(UUID.randomUUID().toString());

        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(account.getId());
        accountResponseDto.setToken(account.getToken());

        log.info("Card credentials checking, has been completed");
        return accountResponseDto;
    }
}
