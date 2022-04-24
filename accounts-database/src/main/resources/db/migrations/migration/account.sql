--liquibase formatted sql

--changeset mls:account
create table account(
    id bigserial,
    balance numeric
);

--changeset  mls:account_balance_positive
alter table account add constraint positive_balance check ( balance > 0 );