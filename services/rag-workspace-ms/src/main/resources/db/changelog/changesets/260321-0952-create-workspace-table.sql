-- liquibase formatted sql

-- changeset dasemenov:260321-0952-create-workspace-table
CREATE TABLE IF NOT EXISTS workspaces
(
    id         UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title      VARCHAR(150) NOT NULL,
    card_type  VARCHAR(20)  NOT NULL DEFAULT 'MEDIUM',
    icon       VARCHAR(10),
    type       VARCHAR(100),
    favourite  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);
-- rollback DROP TABLE workspaces;
