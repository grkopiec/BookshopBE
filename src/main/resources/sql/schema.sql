exec drop_if_table_exists('products');

create table products (
	id number generated always as identity (start with 1 increment by 1),
	name varchar2(50) not null,
	description varchar2(1000)
);

insert into products (name, description)  values ('Java programming', 'This is book that describes programing techniques');