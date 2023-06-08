## Learning Spring Boot Authorization with JWT Auth

This is a simple spring boot project using jwt auth for registration user, login and crud. This project require;
- JDK 11
- PostgreSQL


### Database Configuration
- Create new role user
```
CREATE ROLE postgresdb WITH LOGIN PASSWORD 'Yuhu123’;
```
- Create new schema database
```
psql postgres -U postgresdb
```
- DDL create serial for auto increment id
```
CREATE TABLE public.user (
colname SERIAL
);
```

- Create table users
```
CREATE TABLE public.users (
id SERIAL primary key,
username varchar(40) NULL,
email varchar(25) NULL,
gender varchar(6) NULL,
password varchar(200) NULL
);
```

- Create table products
```
CREATE TABLE public.products (
id varchar(15) primary key,
name varchar(30) NULL,
price varchar(100) NULL
);
```

### Running Spring Boot Application
- Make sure the database configuration in application.properties are matches with your local database configuration.
- For save the token we need redis, run this command to start redis using docker
```
docker compose -f redis.yaml up -d
```

- Run service
```
mvn spring-boot:run
```

- Path url : 
```
  http://localhost:7777/registloginservice/api
```

### API documentation
For full API documentation, please follow this postman collections

[Postman Collections Spring Boot Authorization](https://www.postman.com/collections/56aacbbd02cb80a055c1)

### Frontend web app
https://github.com/indahsramonasari/frontend-user-product-service.git
