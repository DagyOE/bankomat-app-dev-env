package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import com.sanchez.mapper.Mapper;
import com.sanchez.model.Atm;
import com.sanchez.openapi.model.AtmResponse;
import com.sanchez.openapi.model.CardTransactionResponse;
import com.sanchez.openapi.model.WithdrawalResponse;
import com.sanchez.repository.AtmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AtmServiceImpl {

    private final AtmRepository atmRepository;

    public void checkAtmBalance(DelegateExecution execution) {

        String atmId = (String) execution.getVariable(Variable.ATM_ID.getKey());
        Float amount = (Float) execution.getVariable(Variable.AMOUNT.getKey());

        log.info("Start checking atm balance");

        Atm atm = getAtmById(atmId);

        if (amount > atm.getBalance()) {
            log.info("Throwing error \"Insufficient funds in atm\"");
            execution.setVariable(Variable.ERROR_MESSAGE.getKey(), "Token has expired");
            throw new BpmnError("LowAtmBalance");
        }

        log.info("Checking atm balance, has been completed");
    }

    @Transactional
    public void giveOutMoneyAndSubtractBalance(DelegateExecution execution) {

        String atmId = (String) execution.getVariable(Variable.ATM_ID.getKey());
        String errorMessage = (String) execution.getVariable(Variable.ERROR_MESSAGE.getKey());
        Float amount = (Float) execution.getVariable(Variable.AMOUNT.getKey());

        log.info("Start giving out money and subtract balance with amount: {}", amount);

        Atm atm = getAtmById(atmId);

        if (!StringUtils.hasLength(errorMessage)) {
            log.info("Giving out money and subtract balance with amount: {}, has been completed", amount);
            atm.setBalance((atm.getBalance() - amount));
        } else {
            log.info("Giving out money and subtract balance with amount: {}, has been failed", amount);
        }
    }

    public Atm getAtmById(String atmId) {
        log.info("Start getting Atm by id: {}", atmId);

        Atm atm = atmRepository.findAtmById(atmId);

        log.info("Getting Atm by id: {}, has been completed", atmId);
        return atm;
    }

    @Transactional
    public List<AtmResponse> getAllAtms() {
        log.info("Start getting all atms");

        List<Atm> atms = atmRepository.findAll();

        log.info("Getting all atms, has been completed");
        return Mapper.atmListToAtmResponseList(atms);
    }
}
