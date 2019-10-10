DROP TABLE IF EXISTS bookshop.products;
DROP TABLE IF EXISTS bookshop.categories;
DROP TABLE IF EXISTS bookshop.orders;
DROP TABLE IF EXISTS bookshop.users;

CREATE TABLE bookshop.categories (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE bookshop.products (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	producer VARCHAR(100) NOT NULL,
	description VARCHAR(1000),
	price DECIMAL(8,2) NOT NULL,
	discount DECIMAL(8,2) DEFAULT 0 NOT NULL,
	image_path VARCHAR(200),
	category_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE bookshop.users (
	id int NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(1000) NOT NULL,
	last_password_reset DATE,
	authorities VARCHAR(100),
	account_non_expired TINYINT(1) NOT NULL,
	account_non_locked TINYINT(1) NOT NULL,
	credentials_non_expired TINYINT(1) NOT NULL,
	enabled TINYINT(1) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE bookshop.orders (
	id INT NOT NULL AUTO_INCREMENT,
	total_price DECIMAL(8,2) NOT NULL,
	status VARCHAR(26) NOT NULL,
	payment_method VARCHAR(13) NOT NULL,
	shipping_method VARCHAR(15) NOT NULL,
	additional_message VARCHAR(1000),
	paid TINYINT(1) NOT NULL,
	user_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO bookshop.categories (name) VALUES ('Books');
INSERT INTO bookshop.categories (name) VALUES ('Toys');
INSERT INTO bookshop.categories (name) VALUES ('Office articles');
INSERT INTO bookshop.categories (name) VALUES ('Another');

INSERT INTO bookshop.products (name, producer, description, price, discount, category_id, image_path)
	VALUES ('Java programming', 'Hemingway', 'This is book that describes programing techniques', 40.17, 10.00, 1, '/bookshop/resources/0.png');
INSERT INTO bookshop.products (name, producer, description, price, category_id)
	VALUES ('Angular learning', 'Damian Kalbarczyk', 'It presents techniques that use angular', 35.50, 1);
INSERT INTO bookshop.products (name, producer, description, price, category_id, image_path)
	VALUES ('Plastic car', 'Simba', 'Small plastic car, replica of Volkswagen Golf III', 42.00, 2, '/bookshop/resources/2.png');
INSERT INTO bookshop.products (name, producer, description, price, discount, category_id)
	VALUES ('Train', 'Lego', 'Small train with railways', 30.00, 5.20, 2);
INSERT INTO bookshop.products (name, producer, description, price, category_id, image_path)
	VALUES ('Pencil', 'Pencil manufacture', 'Stiff pencil for writing in sheets', 1.15, 3, '/bookshop/resources/4.png');
INSERT INTO bookshop.products (name, producer, description, price, discount, category_id, image_path)
	VALUES ('Black glasses', 'Glasses and sons', 'Glassys that prottect against sun', 10.00, 1.00, 4, '/bookshop/resources/5.png');
	
INSERT INTO bookshop.users (username, password, last_password_reset, authorities, account_non_expired, account_non_locked, credentials_non_expired, enabled)
	VALUES ('admin', '$2a$10$JzNf.Zx0S1hT21jIaKNF.OKulAzg9kwm/Dsw9Keslcpb1oA2HnWU6', NULL, 'ROLE_ADMIN,ROLE_USER', 1, 1, 1, 1);
INSERT INTO bookshop.users (username, password, last_password_reset, authorities, account_non_expired, account_non_locked, credentials_non_expired, enabled)
	VALUES ('user', '$2a$10$JzNf.Zx0S1hT21jIaKNF.OKulAzg9kwm/Dsw9Keslcpb1oA2HnWU6', NULL, 'ROLE_USER', 1, 1, 1, 1);
	
INSERT INTO bookshop.orders (total_price, status, payment_method, shipping_method, additional_message, paid, user_id)
	VALUES (76.67, 'NEW', 'ON_DELIVERY', 'PERSONAL_PICKUP', 'I will come on Monday at 12 AM', 0, 1);
INSERT INTO bookshop.orders (total_price, status, payment_method, shipping_method, additional_message, paid, user_id)
	VALUES (75.45, 'NEW', 'BANK_TRANSFER', 'COURIER', NULL, 0, 1);
