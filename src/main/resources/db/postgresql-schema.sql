CREATE TABLE Person
(
    PERSON_ID        serial NOT NULL PRIMARY KEY,
    FIRST_NAME       varchar(255),
    LAST_NAME        varchar(255),
    TELEPHONE_NUMBER varchar(255),
    LOGIN            varchar(255),
    PASSWORD         varchar(255),
    CREATE_TIME      timestamp
);

CREATE TABLE Account
(
    ACCOUNT_ID serial NOT NULL PRIMARY KEY,
    PERSON_ID  varchar(255),
    NUMBER     varchar(255),
    VALUE      numeric
);

CREATE TABLE Payment
(
    PAYMENT_ID           serial NOT NULL PRIMARY KEY,
    SENDER_ACCOUNT_ID    varchar(255),
    RECIPIENT_ACCOUNT_ID varchar(255),
    RECIPIENT_NAME       varchar(255),
    RECIPIENT_NUMBER     varchar(255),
    AMOUNT               numeric,
    TITLE                varchar(255),
    CREATE_TIME          timestamp
);

CREATE TABLE Address
(
    ADDRESS_ID       serial NOT NULL PRIMARY KEY,
    CITY             varchar(255),
    STREET           varchar(255),
    APARTMENT_NUMBER varchar(255)
);

CREATE TABLE Address
(
    DEPOSIT_ID serial NOT NULL PRIMARY KEY,
    ACCOUNT_ID varchar(255),
    AMOUNT     numeric,
    START_DATE timestamp,
    END_DATE   timestamp,
    TYPE       varchar(255),
    IS_ACTIVE  varchar(255)
);