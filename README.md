# regist-login-service
This is a simple spring boot project using jwt auth for registration user, login and crud
- jdk 11
- db : postgresql 
- create ROLE USER

CREATE ROLE postgresdb WITH LOGIN PASSWORD 'Yuhu123’;

- create new schema database

psql postgres -U postgresdb

- ddl create serial for auto increment id

CREATE TABLE public.tablename (
colname SERIAL
);

- create table users

CREATE TABLE public.users (
id SERIAL primary key,
username varchar(40) NULL,
email varchar(25) NULL,
gender varchar(6) NULL,
password varchar(200) NULL
);

-create table products

CREATE TABLE public.products (
id varchar(15) primary key,
name varchar(30) NULL,
price varchar(100) NULL
);

- make sure the configuration in application.properties are matches with your local configuration
- redis configuration

use redis.yaml in this repository

download and run this command : docker compose -f redis.yaml up -d

# run service
- mvn spring-boot:run
- path url : http://localhost:7777/registloginservice/api

# API documentation
- For full API documentation, this is the postman collections
  https://www.postman.com/collections/56aacbbd02cb80a055c1

# Frontend web app
https://github.com/indahsramonasari/frontend-user-product-service.git
