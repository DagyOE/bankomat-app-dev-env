--liquibase formatted sql

--changeset morty:1

CREATE TABLE IF NOT EXISTS atm(
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    balance FLOAT
);

CREATE TABLE IF NOT EXISTS account(
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    balance FLOAT,
    token VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE,
    locked_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS card(
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    card_number VARCHAR(50),
    account_id VARCHAR(50),
    expiration_date VARCHAR(255),
    cvv VARCHAR(50),
    pin VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE,
    expired_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS account_transaction(
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    account_id VARCHAR(50),
    atm_id VARCHAR(50),
    amount FLOAT,
    balance_before FLOAT,
    balance_after FLOAT,
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (atm_id) REFERENCES atm(id)
);

CREATE TABLE IF NOT EXISTS card_transaction(
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    card_id VARCHAR(50),
    atm_id VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (card_id) REFERENCES card(id),
    FOREIGN KEY (atm_id) REFERENCES atm(id)
);