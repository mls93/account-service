--liquibase formatted sql

--changeset mls:account
create table account(
    id bigserial,
    balance numeric
);