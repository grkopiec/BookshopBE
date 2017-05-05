exec drop_if_table_exists('products');

create table products (
	id number generated always as identity (start with 1 increment by 1),
	name varchar2(100) not null,
	producer varchar2(100) not null,
	description varchar2(1000),
	price number(7,2) not null,
	discount number(7,2) default 0 not null
);

insert into products (name, producer, description, price, discount)
	values ('Java programming', 'Hemingway', 'This is book that describes programing techniques', 40.17, 10);
insert into products (name, producer, description, price)
	values ('Angular learning', 'Damian Kalbarczyk', 'It presents techniques that use angular', 35.50);
insert into products (name, producer, description, price)
	values ('Pencil', 'Pencil manufacture', 'Stiff pencil for writing in sheets', 1.15);
insert into products (name, producer, description, price, discount)
	values ('Black glasses', 'Glasses and sons', 'Glassys that prottect against sun', 10, 1);