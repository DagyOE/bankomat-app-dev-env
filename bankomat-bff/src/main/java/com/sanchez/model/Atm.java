package com.sanchez.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "atm")
public class Atm {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "balance")
    private Float balance;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atm")
    private List<CardTransaction> cardTransactions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atm")
    private List<AccountTransaction> accountTransactions;
}
