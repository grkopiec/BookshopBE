--procedure for droping table if exists
CREATE OR REPLACE PROCEDURE drop_if_table_exists(table_name VARCHAR2) IS
  counter NUMBER := 0;
BEGIN
  SELECT COUNT(*) INTO counter FROM user_tables WHERE table_name = UPPER(table_name);
  IF counter > 0 THEN
    EXECUTE IMMEDIATE 'DROP TABLE ' || table_name || ' CASCADE CONSTRAINTS';
  END IF;
END drop_if_table_exists;
/
--procedure for droping sequence if exists
CREATE OR REPLACE PROCEDURE drop_if_sequence_exists(sequence_name VARCHAR2) IS
  counter NUMBER := 0;
BEGIN
  SELECT COUNT(*) INTO counter FROM all_sequences WHERE sequence_name = UPPER(sequence_name);
  IF counter > 0 THEN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ' || sequence_name;
  END IF;
END drop_if_sequence_exists;
/
EXEC drop_if_table_exists('categories');
EXEC drop_if_table_exists('products');
EXEC drop_if_table_exists('users');
EXEC drop_if_table_exists('orders');

EXEC drop_if_sequence_exists('products_sequence');
EXEC drop_if_sequence_exists('categories_sequence');
EXEC drop_if_sequence_exists('users_sequence');
EXEC drop_if_sequence_exists('orders_sequence');

CREATE SEQUENCE products_sequence
  MINVALUE 0
  START WITH 0
  INCREMENT BY 1;

CREATE SEQUENCE categories_sequence
  MINVALUE 0
  START WITH 0
  INCREMENT BY 1;
  
CREATE SEQUENCE users_sequence
  MINVALUE 0
  START WITH 0
  INCREMENT BY 1;
  
CREATE SEQUENCE orders_sequence
  MINVALUE 0
  START WITH 0
  INCREMENT BY 1;

CREATE TABLE categories (
	id NUMBER,
	name VARCHAR2(100) NOT NULL,
	CONSTRAINT category_id PRIMARY KEY (id)
);

CREATE TABLE products (
	id NUMBER,
	name VARCHAR2(100) NOT NULL,
	producer VARCHAR2(100) NOT NULL,
	description VARCHAR2(1000),
	price NUMBER(8,2) NOT NULL,
	discount NUMBER(8,2) DEFAULT 0 NOT NULL,
	image_path VARCHAR2(200),
	category_id,
	CONSTRAINT product_id_pk PRIMARY KEY (id),
	CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users (
	id NUMBER,
	username VARCHAR2(30) NOT NULL,
	password VARCHAR2(1000) NOT NULL,
	last_password_reset DATE,
	authorities VARCHAR2(100),
	account_non_expired NUMBER(1) NOT NULL,
	account_non_locked NUMBER(1) NOT NULL,
	credentials_non_expired NUMBER(1) NOT NULL,
	enabled NUMBER(1) NOT NULL,
	CONSTRAINT user_id_pk PRIMARY KEY (id)
);

CREATE TABLE orders (
	id NUMBER,
	total_price NUMBER(8,2) NOT NULL,
	status VARCHAR2(18) NOT NULL,
	payment_method VARCHAR2(13) NOT NULL,
	shippingMethod VARCHAR2(15) NOT NULL,
	paid NUMBER(1) NOT NULL,
	user_id,
	CONSTRAINT order_id_pk PRIMARY KEY (id),
	CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO categories (id, name) VALUES (categories_sequence.nextval, 'Books');
INSERT INTO categories (id, name) VALUES (categories_sequence.nextval, 'Toys');
INSERT INTO categories (id, name) VALUES (categories_sequence.nextval, 'Office articles');
INSERT INTO categories (id, name) VALUES (categories_sequence.nextval, 'Another');

INSERT INTO products (id, name, producer, description, price, discount, category_id, image_path)
	VALUES (products_sequence.nextval, 'Java programming', 'Hemingway', 'This is book that describes programing techniques', 40.17, 10.00, 0, '/bookshop/resources/0.png');
INSERT INTO products (id, name, producer, description, price, category_id)
	VALUES (products_sequence.nextval, 'Angular learning', 'Damian Kalbarczyk', 'It presents techniques that use angular', 35.50, 0);
INSERT INTO products (id, name, producer, description, price, category_id, image_path)
	VALUES (products_sequence.nextval, 'Plastic car', 'Simba', 'Small plastic car, replica of Volkswagen Golf III', 42.00, 1, '/bookshop/resources/2.png');
INSERT INTO products (id, name, producer, description, price, discount, category_id)
	VALUES (products_sequence.nextval, 'Train', 'Lego', 'Small train with railways', 30.00, 5.20, 1);
INSERT INTO products (id, name, producer, description, price, category_id, image_path)
	VALUES (products_sequence.nextval, 'Pencil', 'Pencil manufacture', 'Stiff pencil for writing in sheets', 1.15, 2, '/bookshop/resources/4.png');
INSERT INTO products (id, name, producer, description, price, discount, category_id, image_path)
	VALUES (products_sequence.nextval, 'Black glasses', 'Glasses and sons', 'Glassys that prottect against sun', 10.00, 1.00, 3, '/bookshop/resources/5.png');
	
INSERT INTO users (id, username, password, last_password_reset, authorities, account_non_expired, account_non_locked, credentials_non_expired, enabled)
	VALUES (users_sequence.nextval, 'admin', '$2a$10$JzNf.Zx0S1hT21jIaKNF.OKulAzg9kwm/Dsw9Keslcpb1oA2HnWU6', NULL, 'ROLE_ADMIN,ROLE_USER', 1, 1, 1, 1);
INSERT INTO users (id, username, password, last_password_reset, authorities, account_non_expired, account_non_locked, credentials_non_expired, enabled)
	VALUES (users_sequence.nextval, 'user', '$2a$10$JzNf.Zx0S1hT21jIaKNF.OKulAzg9kwm/Dsw9Keslcpb1oA2HnWU6', NULL, 'ROLE_USER', 1, 1, 1, 1);
	
INSERT INTO orders (id, total_price, status, payment_method, paid, user_id)
	VALUES (orders_sequence.nextval, 76.67, 'NEW', 'ON_DELIVERY', 'PERSONAL_PICKUP', 0, 0);
INSERT INTO orders (id, total_price, status, payment_method, paid, user_id)
	VALUES (orders_sequence.nextval, 75.45, 'NEW', 'BANK_TRANSFER', 'COURIER', 0, 0);