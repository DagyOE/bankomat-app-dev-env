package com.sanchez.service;

import com.sanchez.dtos.AccountResponseDto;
import com.sanchez.dtos.ResponseMessageDto;
import com.sanchez.openapi.model.CardVerificationResponse;
import com.sanchez.openapi.model.WithdrawalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl {

    public ResponseMessageDto createMessageForBadPinCode() {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("PIN code is not correct");
        response.setStatus(CardVerificationResponse.StatusEnum.CANCELLED);
        return response;
    }

    public ResponseMessageDto createMessageForExpiredCard() {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("Card Expiration date is different");
        response.setStatus(CardVerificationResponse.StatusEnum.INVALIDCARD);
        return response;
    }

    public ResponseMessageDto createMessageForInsufficientFundsAccount() {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("Insufficient funds on account");
        response.setStatus(CardVerificationResponse.StatusEnum.INSUFFICIENTFUNDS);
        return response;
    }

    public ResponseMessageDto createMessageForInsufficientFundsAtm() {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("Insufficient funds in atm");
        response.setStatus(CardVerificationResponse.StatusEnum.INSUFFICIENTFUNDS);
        return response;
    }

    public ResponseMessageDto createMessageForExpiredToken() {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("Token has expired");
        response.setStatus(CardVerificationResponse.StatusEnum.TIMEOUT);
        return response;
    }

    public CardVerificationResponse buildResponseMessage(ResponseMessageDto responseMessage, AccountResponseDto accountResponseDto) {
        CardVerificationResponse response = new CardVerificationResponse();

        if (Objects.nonNull(responseMessage) || Objects.nonNull(accountResponseDto)) {
            if (Objects.isNull(accountResponseDto) || accountResponseDto.equals(null)) {
                response.setMessage(responseMessage.getMessage());
                response.setStatus(responseMessage.getStatus());
            } else {
                response.setAccountId(accountResponseDto.getAccountId());
                response.token(accountResponseDto.getToken());
                response.status(CardVerificationResponse.StatusEnum.SUCCESS);
            }

            return response;
        }

        return null;
    }

    public WithdrawalResponse buildWithdrawalResponseMessage(ResponseMessageDto responseMessage) {
        WithdrawalResponse response = new WithdrawalResponse();

        response.setTransactionId(response.getTransactionId());

        if (Objects.nonNull(responseMessage)) {
            response.setMessage(responseMessage.getMessage());
            response.setStatus(WithdrawalResponse.StatusEnum.fromValue(responseMessage.getStatus().getValue()));
        } else {
            response.setMessage("Withdrawal has been completed");
            response.setStatus(WithdrawalResponse.StatusEnum.SUCCESS);
        }

        return response;
    }
}
