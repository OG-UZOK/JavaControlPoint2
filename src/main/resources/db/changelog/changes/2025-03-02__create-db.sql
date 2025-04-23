-- liquibase formatted sql

-- changeset Ivan:1
-- Создание таблицы "Расписание"
CREATE TABLE schedule (
    id VARCHAR(32) PRIMARY KEY,
    schedule_name VARCHAR(255),
    creation_date TIMESTAMPTZ NOT NULL,
    update_date TIMESTAMPTZ NOT NULL
);

-- changeset Ivan:2
-- Создание таблицы "Шаблон расписания"
CREATE TABLE schedule_template (
    id VARCHAR(32) PRIMARY KEY,
    creation_date TIMESTAMPTZ NOT NULL,
    template_type VARCHAR(2) NOT NULL
);

-- changeset Ivan:3
-- Создание таблицы "Слот расписания"
CREATE TABLE schedule_slot (
    id VARCHAR(32) PRIMARY KEY,
    schedule_template_id VARCHAR(32) NOT NULL,
    begin_time TIMETZ NOT NULL,
    end_time TIMETZ NOT NULL,
    CONSTRAINT fk_schedule_template
        FOREIGN KEY (schedule_template_id)
        REFERENCES schedule_template(id)
        ON DELETE CASCADE
);

-- changeset Ivan:4
-- Создание таблицы "Период расписания"
CREATE TABLE schedule_period (
    id VARCHAR(32) PRIMARY KEY,
    slot_id VARCHAR(32) NOT NULL,
    schedule_id VARCHAR(32) NOT NULL,
    slot_type VARCHAR(20) NOT NULL,
    administrator_id VARCHAR(32) NOT NULL,
    executor_id VARCHAR(32),
    CONSTRAINT fk_schedule_slot
        FOREIGN KEY (slot_id)
        REFERENCES schedule_slot(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_schedule
        FOREIGN KEY (schedule_id)
        REFERENCES schedule(id)
        ON DELETE CASCADE
);

-- changeset Ivan:5
-- Создание таблицы "Сотрудники"
CREATE TABLE employee (
    id VARCHAR(32) PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    position VARCHAR(20) NOT NULL
);

-- changeset Ivan:6
-- Добавление ограничений на возможные значения для slot_type в таблице schedule_period
ALTER TABLE schedule_period
ADD CONSTRAINT chk_slot_type
CHECK (slot_type IN ('LOCAL', 'FROM HOME', 'UNDEFINED'));

-- changeset Ivan:7
-- Добавление ограничений на возможные значения для status в таблице employee
ALTER TABLE employee
ADD CONSTRAINT chk_status
CHECK (status IN ('WORKING', 'TRIAL', 'TIME_OFF', 'DISMISSED'));

-- changeset Ivan:8
-- Добавление ограничений на возможные значения для position в таблице employee
ALTER TABLE employee
ADD CONSTRAINT chk_position
CHECK (position IN ('MANAGER', 'EMPLOYEE', 'UNDEFINED', 'TECH'));