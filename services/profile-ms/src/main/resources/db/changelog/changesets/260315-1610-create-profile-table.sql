-- liquibase formatted sql

-- changeset dasemenov:260315-1610-create-profile-table
CREATE TABLE IF NOT EXISTS profile
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    surname       VARCHAR(100) NOT NULL,
    date_of_birth DATE         NOT NULL,
    created_at    TIMESTAMPTZ DEFAULT NOW(),
    updated_at    TIMESTAMPTZ DEFAULT NOW()
);
-- rollback DROP TABLE profile;
