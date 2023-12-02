package com.sanchez.repository;

import com.sanchez.model.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, String> {
    public Atm findAtmById(String id);
}
