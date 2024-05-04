create table operations
(
    operation_id   serial
        primary key,
    user_id        integer
        references users,
    operation_type integer        not null
        constraint check_operation_type
            check (operation_type = ANY (ARRAY [0, 1, 2])),
    amount         numeric(10, 2) not null,
    operation_date timestamp      not null,
    description    text
);

alter table operations
    owner to postgres;

