drop table customer if exists cascade;
create table customer(
ID	bigint primary key not null identity,
FIRST_NAME	varchar(255) not null,
LAST_NAME	varchar(255) not null
);

drop table purchase if exists cascade;
create table purchase (
ID	bigint primary key not null identity,
orderdate	date not null,
customer_id bigint,
address_id bigint
);

ALTER TABLE purchase
    ADD FOREIGN KEY (customer_id)
    REFERENCES customer (id)
    ON DELETE set null;

ALTER TABLE purchase
    ADD FOREIGN KEY (address_id)
    REFERENCES address (id)
    ON DELETE set null;

drop table product if exists cascade;
create table product (
ID	bigint primary key not null identity,
TITLE varchar(255) not null,
NOTE clob,
PRICE double not null
);

drop table ADDRESS if exists cascade;
create table ADDRESS (
ID	bigint primary key not null identity,
name varchar(255) not null,
street varchar(255) not null,
city varchar(255) not null,
country varchar(255) not null
);


drop table rel_purchase_product if exists cascade;
create table rel_purchase_product (
purchase_id bigint,
product_id bigint,
amount int
);

ALTER TABLE rel_purchase_product
    ADD FOREIGN KEY (purchase_id)
    REFERENCES purchase (id)
    ON DELETE set null;

ALTER TABLE rel_purchase_product
    ADD FOREIGN KEY (product_id)
    REFERENCES product (id)
    ON DELETE set null;

