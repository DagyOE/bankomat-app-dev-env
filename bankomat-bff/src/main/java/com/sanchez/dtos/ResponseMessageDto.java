package com.sanchez.dtos;

import com.sanchez.openapi.model.CardVerificationResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMessageDto implements Serializable {
    public String message;
    public CardVerificationResponse.StatusEnum status;
}
