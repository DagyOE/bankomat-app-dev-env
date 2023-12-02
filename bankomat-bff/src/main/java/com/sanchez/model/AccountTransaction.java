package com.sanchez.model;

import com.sanchez.model.converter.AccountTransactionStatusEnumConverter;
import com.sanchez.openapi.model.AccountTransactionResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_transaction")
public class AccountTransaction {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "balance_before")
    private Float balanceBefore;

    @Column(name = "balance_after")
    private Float balanceAfter;

    @Column(name = "status")
    @Convert(converter = AccountTransactionStatusEnumConverter.class)
    private AccountTransactionResponse.StatusEnum status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private Account account;

    @ManyToOne
    @JoinColumn(name = "atm_id")
    @ToString.Exclude
    private Atm atm;
}
