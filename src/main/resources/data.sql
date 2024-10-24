create table Person (
    id int not null,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    birth_date date not null,
    employed varchar(3) not null,
    occupation varchar(30) null,
    address_id int not null,
    primary key(id)
);