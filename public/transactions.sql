create table transactions
(
    id               serial
        primary key,
    sender_id        integer                             not null
        references users,
    recipient_id     integer                             not null
        references users,
    amount           numeric(10, 2)                      not null,
    operation_type   integer                             not null
        constraint transactions_operation_type_check
            check (operation_type = ANY (ARRAY [0, 1, 2])),
    timestamp        timestamp default CURRENT_TIMESTAMP not null,
    transaction_date date                                not null,
    status           text      default 'PENDING'::text   not null
);

alter table transactions
    owner to postgres;

