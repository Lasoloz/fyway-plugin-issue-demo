-- # Example table for first migration
create table if not exists admin
(
    id       bigserial primary key not null,
    username varchar(64)           not null
        constraint admin_username_unique
            unique,
    password varchar(144)          not null
);
