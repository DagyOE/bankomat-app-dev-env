package com.sanchez.service;

import com.sanchez.mapper.Mapper;
import com.sanchez.model.Atm;
import com.sanchez.openapi.model.AtmResponse;
import com.sanchez.openapi.model.WithdrawalResponse;
import com.sanchez.repository.AtmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AtmServiceImpl {

    private final AtmRepository atmRepository;

    public boolean checkAtmBalance(Float amount, String atmId) {
        log.info("Start checking atm balance");

        Atm atm = getAtmById(atmId);

        if (amount > atm.getBalance()) {
            log.info("Throwing error \"Insufficient funds in atm\"");
            return false;
        }

        log.info("Checking atm balance, has been completed");
        return true;
    }

    @Transactional
    public void giveOutMoneyAndSubtractBalance(Float amount, String atmId,
                                               WithdrawalResponse.StatusEnum responseStatus) {
        log.info("Start giving out money and subtract balance with amount: {}", amount);

        Atm atm = getAtmById(atmId);

        if (responseStatus.equals(WithdrawalResponse.StatusEnum.SUCCESS)) {
            atm.setBalance((atm.getBalance() - amount));
        }

        log.info("Giving out money and subtract balance with amount: {}, has been completed", amount);
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
