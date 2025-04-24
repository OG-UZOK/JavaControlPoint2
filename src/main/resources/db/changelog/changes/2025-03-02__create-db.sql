-- liquibase formatted sql

-- changeset Ivan:1
-- Создание таблицы "employees"
CREATE TABLE employees (
   id UUID PRIMARY KEY,
   name VARCHAR(255),
   role VARCHAR(50)
);

-- changeset Ivan:2
-- Создание таблицы "salary_role"
CREATE TABLE salary_role (
    id UUID PRIMARY KEY,
    coefficient DOUBLE PRECISION
);

-- changeset Ivan:3
-- Создание таблицы "exchange_rates"
CREATE TABLE exchange_rates (
    id UUID PRIMARY KEY,
    base_currency CHAR(3),
    currency CHAR(3),
    rate DECIMAL(19,6),
    timestamp TIMESTAMP
);

-- changeset Ivan:4
-- Создание таблицы "reports"
CREATE TABLE reports (
     id UUID PRIMARY KEY,
     start_date DATE,
     end_date DATE,
     employee_id UUID REFERENCES employees(id),
     hours DOUBLE PRECISION,
     salary DOUBLE PRECISION
);