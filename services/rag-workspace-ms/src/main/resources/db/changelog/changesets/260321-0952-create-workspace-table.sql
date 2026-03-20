-- liquibase formatted sql

-- changeset dasemenov:260321-0952-create-workspace-table
CREATE TABLE IF NOT EXISTS workspaces
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title      VARCHAR(150) NOT NULL,
    icon       VARCHAR(10),
    type       VARCHAR(100),
    created_at TIMESTAMP        DEFAULT NOW()
);
-- rollback DROP TABLE workspaces;
