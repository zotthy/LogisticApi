INSERT INTO application_user (first_name, last_name, email, password) VALUES ('Jan', 'Kowalski', 'test@admin.com', '$2a$10$dW7tBVAoVIE.g8ScLZ8SReFH7Aif6r7G3w4aTrHrfWyXhLTCgGQFe');
INSERT INTO application_user (first_name, last_name, email, password) VALUES ('Jan', 'Kowalski', 'user@admin.com', '$2a$10$dW7tBVAoVIE.g8ScLZ8SReFH7Aif6r7G3w4aTrHrfWyXhLTCgGQFe');


INSERT INTO user_role (name, description) VALUES ('ADMIN', 'Ma dostęp do wszystkiego');
INSERT INTO user_role (name, description) VALUES ('USER', 'Dostęp tylko do odczytu');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id,role_id) values  (2, 2);

INSERT INTO cargo_address (id, street, city, zip_code) VALUES (1, 'Broadway', 'New York', '00666');

INSERT INTO cargo_address (id, street, city, zip_code) VALUES (2, 'Market Street', 'San Francisco', '00999');

INSERT INTO cargo (id, price, status, load_address_id, unload_address_id) VALUES (1, 999.99 , 'Delivered', 1, 2);