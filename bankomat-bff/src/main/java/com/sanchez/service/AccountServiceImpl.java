package com.sanchez.service;

import com.sanchez.enumerate.Variable;
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
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void checkAccountBalance(DelegateExecution execution) {

        String accountId = (String) execution.getVariable(Variable.ACCOUNT_ID.getKey());
        Float amount = (Float) execution.getVariable(Variable.AMOUNT.getKey());

        log.info("Start checking account balance before withdrawal, accountId: {}", accountId);

        Account account = getAccountByAccountId(accountId);

        if ((account.getBalance()-amount) < 0F) {
            log.info("Throwing error \"Insufficient funds on your account\"");
            execution.setVariable(Variable.ERROR_MESSAGE.getKey(), "Insufficient funds on your account");
            throw new BpmnError("LowAccountBalance");
        }

        log.info("Checking account balance before withdrawal, accountId: {}, has been completed", accountId);
    }

    @Transactional
    public void changeBalanceOnAccount(DelegateExecution execution) {

        String accountId = (String) execution.getVariable(Variable.ACCOUNT_ID.getKey());
        String token = (String) execution.getVariable(Variable.TOKEN.getKey());
        Float amount = (Float) execution.getVariable(Variable.AMOUNT.getKey());

        log.info("Start changing balance on account with id: {}", accountId);

        Account account = getAccountByAccountId(accountId);

        if (Objects.isNull(account.getToken()) || !account.getToken().equals(token)) {
            log.info("Throwing error \"Token has expired\"");
            execution.setVariable(Variable.ERROR_MESSAGE.getKey(), "Token has expired");
            throw new BpmnError("ExpiredToken");
        }

        account.setBalance((account.getBalance() - amount));

        log.info("Changing balance on account with id: {}, has been completed", accountId);
    }

    public void createAccountTransaction(DelegateExecution execution) {

        String accountId = (String) execution.getVariable(Variable.ACCOUNT_ID.getKey());
        String transactionId = (String) execution.getVariable(Variable.TRANSACTION_ID.getKey());
        String atmId = (String) execution.getVariable(Variable.ATM_ID.getKey());
        String errorMessage = (String) execution.getVariable(Variable.ERROR_MESSAGE.getKey());
        Float amount = (Float) execution.getVariable(Variable.AMOUNT.getKey());

        log.info("Start creating account transaction after withdrawal, accountId: {}", accountId);

        AccountTransaction accountTransaction = new AccountTransaction();
        Account account = getAccountByAccountId(accountId);

        accountTransaction.setId(transactionId);
        accountTransaction.setAccount(account);
        accountTransaction.setAtm(atmService.getAtmById(atmId));
        accountTransaction.setAmount(amount);
        accountTransaction.setBalanceAfter(account.getBalance());
        if (StringUtils.hasLength(errorMessage)) {
            accountTransaction.setBalanceBefore(account.getBalance());
            accountTransaction.setStatus(AccountTransactionResponse.StatusEnum.CANCELLED);
        } else {
            accountTransaction.setBalanceBefore(account.getBalance() + amount);
            accountTransaction.setStatus(AccountTransactionResponse.StatusEnum.SUCCESS);
        }
        accountTransaction.setCreatedAt(OffsetDateTime.now());

        accountTransactionRepository.save(accountTransaction);

        log.info("Creating account transaction after withdrawal, accountId: {}, has been completed", accountId);
    }

    @Transactional
    public void removeTokenFromAccount(DelegateExecution execution) {

        String accountId = (String) execution.getVariable(Variable.ACCOUNT_ID.getKey());

        log.info("Start removing token from account with id: {}", accountId);

        Account account = getAccountByAccountId(accountId);
        account.setToken(null);

        log.info("Start removing token from account with id: {}", accountId);
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
}
