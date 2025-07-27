-- # Second table
create table if not exists image
(
    id             bigserial primary key not null,
    ref            varchar(64)           not null,
    base64_content varchar(4098)         not null
);

create unique index if not exists idx_image__ref on image (ref);
