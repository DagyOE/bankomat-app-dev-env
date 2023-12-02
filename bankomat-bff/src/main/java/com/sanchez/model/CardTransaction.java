package com.sanchez.model;

import com.sanchez.model.converter.CardTransactionStatusEnumConverter;
import com.sanchez.openapi.model.CardTransactionResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_transaction")
public class CardTransaction {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "status")
    @Convert(converter = CardTransactionStatusEnumConverter.class)
    private CardTransactionResponse.StatusEnum status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @ToString.Exclude
    private Card card;

    @ManyToOne
    @JoinColumn(name = "atm_id")
    @ToString.Exclude
    private Atm atm;
}
