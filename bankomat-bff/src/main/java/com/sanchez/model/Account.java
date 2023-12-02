package com.sanchez.model;

import com.sanchez.model.converter.AccountStatusEnumConverter;
import com.sanchez.openapi.model.AccountResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "token")
    private String token;

    @Column(name = "status")
    @Convert(converter = AccountStatusEnumConverter.class)
    private AccountResponse.StatusEnum status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "locked_at")
    private OffsetDateTime lockedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private List<Card> cards;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private List<AccountTransaction> accountTransactions;
}
