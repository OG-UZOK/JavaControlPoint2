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
     start_date DATE NOT NULL,
     end_date DATE,
     created_at TIMESTAMP
);

-- changeset Ivan:5
-- Создание таблицы report_entries
CREATE TABLE report_entries (
    id UUID PRIMARY KEY,
    report_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    hours DOUBLE PRECISION,
    salary DOUBLE PRECISION
);

-- Внешние ключи
ALTER TABLE report_entries
    ADD CONSTRAINT fk_entry_report
        FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE;

ALTER TABLE report_entries
    ADD CONSTRAINT fk_entry_employee
        FOREIGN KEY (employee_id) REFERENCES employees(id);

-- Индексы для ускорения поиска
CREATE INDEX idx_report_entries_report_id ON report_entries(report_id);
CREATE INDEX idx_report_entries_employee_id ON report_entries(employee_id);
CREATE INDEX idx_reports_date_range ON reports(start_date, end_date);