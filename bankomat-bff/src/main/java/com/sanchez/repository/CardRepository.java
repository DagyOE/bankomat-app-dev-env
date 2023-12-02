package com.sanchez.repository;

import com.sanchez.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    public Card getCardByCardNumber(String cardNumber);
}
