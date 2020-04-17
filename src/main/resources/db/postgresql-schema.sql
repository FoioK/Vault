CREATE SCHEMA IF NOT EXISTS vault

    CREATE TABLE Person
    (
        PERSON_ID        serial NOT NULL PRIMARY KEY,
        FIRST_NAME       varchar(255),
        LAST_NAME        varchar(255),
        TELEPHONE_NUMBER varchar(255),
        LOGIN            varchar(255),
        PASSWORD         varchar(255),
        CREATE_TIME      timestamp
    )