drop table if exists roles;
drop table if exists users;
drop table if exists bank_account;
drop table if exists business_area;
drop table if exists categories;
drop table if exists clients;
drop table if exists companies;
drop table if exists invoices;
drop table if exists payment;
drop table if exists product;
drop table if exists service_types;
drop table if exists tags;

create table roles
(
    role_id bigserial primary key,
    name    varchar(255)
);

create table business_area
(
    id bigserial primary key,
    area             varchar(255)
);

create table companies
(
    company_id   bigserial
        primary key,
    company_name varchar(255)
);

create table users
(
    user_id          bigserial primary key,
    address          varchar(255),
    created          timestamp,
    deleted          boolean not null,
    email            varchar(255) unique,
    enabled          boolean not null,
    expiry_date      timestamp,
    first_name       varchar(255),
    is_active        boolean not null,
    last_name        varchar(255),
    password         varchar(255),
    token            varchar(255),
    update_at        timestamp,
    business_area_id bigint references business_area,
    company_name_id  bigint references companies
);

create table users_roles
(
    user_id bigint not null references users,
    role_id bigint not null references roles
);

create table tags
(
    tag_id      bigserial primary key,
    description varchar(255),
    name_tag    varchar(255)
);

create table categories
(
    id                 bigserial primary key,
    description        varchar(255),
    is_income_category boolean,
    title              varchar(255)
);

create table service_types
(
    id           bigserial primary key,
    service_type varchar(255)
);

create table clients
(
    client_id      bigserial primary key,
    address        varchar(255),
    client_name    varchar(255),
    company_name   varchar(255),
    created        timestamp,
    email          varchar(255),
    income         boolean not null,
    phone_number   varchar(255),
    seller_surname varchar(255)
);

create table client_tag
(
    clients_id bigint not null references clients,
    tags_id    bigint not null references tags
);

create table products
(
    id              bigserial primary key,
    description     varchar(255),
    is_income       boolean,
    price           double precision not null,
    title           varchar(255),
    category_id     bigint references categories,
    service_type_id bigint references service_types
);

create table invoices
(
    id               bigserial primary key,
    date_of_creation timestamp,
    description      varchar(255),
    end_date         timestamp,
    start_date       timestamp,
    status           varchar(255),
    sum              double precision,
    title            varchar(255),
    client_id        bigint references clients
);

create table invoices_products
(
    invoice_id bigint not null references invoices,
    product_id bigint not null references products
);

create table bank_account
(
    id                  bigserial primary key,
    bank_account_name   varchar(255),
    bank_account_number varchar(255),
    description         varchar(255)
);

create table payment
(
    payment_id      bigserial primary key,
    amount_of_money double precision,
    comment         varchar(255),
    created         timestamp,
    payment_date    timestamp,
    payment_file    varchar(255),
    type_of_pay     varchar(255),
    bank_account_id bigint references bank_account,
    invoice_id      bigint references invoices
);


