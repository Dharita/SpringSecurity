DROP DATABASE IF EXISTS user_database;

CREATE DATABASE user_database;

USE user_database ;  

CREATE TABLE tbl_users (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(25),
  email  VARCHAR(50),
  username VARCHAR(25),
  password VARCHAR(225),
  gender VARCHAR(1),
  contact_number LONG,
  address VARCHAR(150),
  country VARCHAR(20),
  newsletter BOOLEAN,
  framework VARCHAR(500),
  skill VARCHAR(500),
  role_id INTEGER,
  PRIMARY KEY (id)
);

INSERT INTO tbl_users 
(name, email, username, password, gender, contact_number, address, country, newsletter, framework, skill, role_id) 
VALUES 
('Admin User', 'admin@gmail.com', 'admin', '$2a$10$WOf9uuaNfUgqpfXrfK1QiO.scUjxJMA.wENEu4c8GJMbPhFwbxMwu', 'f', 919979294062, 'Ahmedabad', 'India', true, 'Spring MVC', 'Spring', 1);

INSERT INTO tbl_users 
(name, email, username, password, gender, contact_number, address, country, newsletter, framework, skill, role_id) 
VALUES 
('Developer User', 'developer@gmail.com', 'developer', '$2a$10$WOf9uuaNfUgqpfXrfK1QiO.scUjxJMA.wENEu4c8GJMbPhFwbxMwu', 'm', 919601610180, 'Ahmedabad', 'India', false, 'Spring MVC', 'Spring', 2);

CREATE TABLE tbl_roles (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(25)
);

INSERT INTO tbl_roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO tbl_roles (name) VALUES ('ROLE_USER');