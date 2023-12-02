package com.sanchez.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountResponseDto implements Serializable {
    public String accountId;
    public String token;
}
