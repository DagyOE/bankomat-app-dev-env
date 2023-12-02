package com.sanchez.service;

import com.sanchez.mapper.Mapper;
import com.sanchez.model.Account;
import com.sanchez.model.AccountTransaction;
import com.sanchez.model.Card;
import com.sanchez.openapi.model.AccountResponse;
import com.sanchez.openapi.model.AccountTransactionResponse;
import com.sanchez.openapi.model.WithdrawalResponse;
import com.sanchez.repository.AccountRepository;
import com.sanchez.repository.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final CardServiceImpl cardService;
    private final AtmServiceImpl atmService;

    public boolean checkAccountBalance(String accountId, Float amount) {
        log.info("Start checking account balance before withdrawal, accountId: {}", accountId);

        Account account = getAccountByAccountId(accountId);

        if ((account.getBalance()-amount) < 0) {
            log.info("Throwing error \"Insufficient funds on your account\"");
            return false;
        }

        log.info("Checking account balance before withdrawal, accountId: {}, has been completed", accountId);
        return true;
    }

    @Transactional
    public boolean changeBalanceOnAccount(String accountId, String token, Float amount) {
        log.info("Start changing balance on account with id: {}", accountId);

        Account account = getAccountByAccountId(accountId);

        if (Objects.isNull(account.getToken()) || !account.getToken().equals(token)) {
            log.info("Throwing error \"Token has expired\"");
            return false;
        }

        account.setBalance((account.getBalance() - amount));

        log.info("Changing balance on account with id: {}, has been completed", accountId);
        return true;
    }

    public void createAccountTransaction(String transactionId, String accountId, String atmId,
                                         Float amount, WithdrawalResponse.StatusEnum responseStatus) {
        log.info("Start creating account transaction after withdrawal, accountId: {}", accountId);

        AccountTransaction accountTransaction = new AccountTransaction();
        Account account = getAccountByAccountId(accountId);

        accountTransaction.setId(transactionId);
        accountTransaction.setAccount(account);
        accountTransaction.setAtm(atmService.getAtmById(atmId));
        accountTransaction.setAmount(responseStatus == WithdrawalResponse.StatusEnum.SUCCESS ? amount : 0L);
        accountTransaction.setBalanceBefore(responseStatus == WithdrawalResponse.StatusEnum.SUCCESS ?
                (account.getBalance() + amount) : account.getBalance());
        accountTransaction.setBalanceAfter(account.getBalance());
        accountTransaction.setStatus(responseStatus == WithdrawalResponse.StatusEnum.SUCCESS  ?
                AccountTransactionResponse.StatusEnum.SUCCESS :
                AccountTransactionResponse.StatusEnum.fromValue(responseStatus.getValue()
        ));
        accountTransaction.setCreatedAt(OffsetDateTime.now());

        accountTransactionRepository.save(accountTransaction);

        log.info("Creating account transaction after withdrawal, accountId: {}, has been completed", accountId);
    }

    public Account getAccountByCardInformation(String cardNumber) {
        log.info("Start getting account information from DB by cardNumber: {}", cardNumber);

        Card card = cardService.getCardByCardNumber(cardNumber);

        Account account = accountRepository.findAccountById(card.getAccount().getId());

        log.info("Getting account information from DB by cardNumber: {}, has been completed", cardNumber);
        return account;
    }

    public Account getAccountByAccountId(String accountId) {
        log.info("Start getting account information from DB by accountId: {}", accountId);

        Account account = accountRepository.findAccountById(accountId);

        log.info("Getting account information from DB by accountId: {}, has been completed", accountId);
        return account;
    }

    @Transactional
    public List<AccountResponse> getAllAccounts() {
        log.info("Start getting all accounts");

        List<Account> accounts = accountRepository.findAll();

        log.info("Getting all accounts, has been completed");
        return Mapper.accountListToAccountResponseList(accounts);
    }

    @Transactional
    public void removeTokenFromAccount(String accountId) {
        log.info("Start removing token from account with id: {}", accountId);

        Account account = getAccountByAccountId(accountId);
        account.setToken(null);

        log.info("Start removing token from account with id: {}", accountId);
    }
}
