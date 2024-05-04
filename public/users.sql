create table users
(
    id      serial
        primary key,
    balance numeric(10, 2) default 0.00 not null
);

alter table users
    owner to postgres;

