EXEC drop_if_table_exists('categories');
EXEC drop_if_table_exists('products');

CREATE TABLE categories (
	id NUMBER GENERATED ALWAYS AS IDENTITY MINVALUE 0 START WITH 0 INCREMENT BY 1,
	name VARCHAR2(100) NOT NULL,
	CONSTRAINT category_id PRIMARY KEY (id)
);

CREATE TABLE products (
	id NUMBER GENERATED ALWAYS AS IDENTITY MINVALUE 0 START WITH 0 INCREMENT BY 1,
	name VARCHAR2(100) NOT NULL,
	producer VARCHAR2(100) NOT NULL,
	description VARCHAR2(1000),
	price NUMBER(7,2) NOT NULL,
	discount NUMBER(7,2) DEFAULT 0 NOT NULL,
	image_path VARCHAR2(200),
	category_id,
	CONSTRAINT product_id_pk PRIMARY KEY (id),
	CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES categories(id)
);

INSERT INTO categories (name) VALUES ('Books');
INSERT INTO categories (name) VALUES ('Toys');
INSERT INTO categories (name) VALUES ('Office articles');
INSERT INTO categories (name) VALUES ('Another');

INSERT INTO products (name, producer, description, price, discount, category_id, image_path)
	VALUES ('Java programming', 'Hemingway', 'This is book that describes programing techniques', 40.17, 10.00, 0, '/bookshop/resources/0.png');
INSERT INTO products (name, producer, description, price, category_id)
	VALUES ('Angular learning', 'Damian Kalbarczyk', 'It presents techniques that use angular', 35.50, 0);
INSERT INTO products (name, producer, description, price, category_id, image_path)
	VALUES ('Plastic car', 'Simba', 'Small plastic car, replica of Volkswagen Golf III', 42.00, 1, '/bookshop/resources/2.png');
INSERT INTO products (name, producer, description, price, discount, category_id)
	VALUES ('Train', 'Lego', 'Small train with railways', 30.00, 5.20, 1);
INSERT INTO products (name, producer, description, price, category_id, image_path)
	VALUES ('Pencil', 'Pencil manufacture', 'Stiff pencil for writing in sheets', 1.15, 2, '/bookshop/resources/4.png');
INSERT INTO products (name, producer, description, price, discount, category_id, image_path)
	VALUES ('Black glasses', 'Glasses and sons', 'Glassys that prottect against sun', 10.00, 1.00, 3, '/bookshop/resources/5.png');
	
--procedure for droping table if exists
--CREATE OR REPLACE PROCEDURE drop_if_table_exists(table_name VARCHAR2) IS
--  counter NUMBER := 0;
--begin
--  SELECT COUNT(*) INTO counter FROM user_tables WHERE table_name = UPPER(table_name);
--  IF counter > 0 THEN
--    EXECUTE IMMEDIATE 'DROP TABLE ' || table_name || ' CASCADE CONSTRAINTS';
--  END IF;
--END drop_if_table_exists;