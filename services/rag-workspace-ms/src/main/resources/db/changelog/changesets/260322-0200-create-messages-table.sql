-- liquibase formatted sql

-- changeset dasemenov:260322-0200-create-messages-table
CREATE TABLE IF NOT EXISTS messages
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    content       VARCHAR(255) NOT NULL,
    owned_by_user BOOLEAN      NOT NULL,
    created_at    TIMESTAMP        DEFAULT NOW(),
    workspace_id  UUID         NOT NULL REFERENCES workspaces (id) ON DELETE CASCADE
);
-- rollback DROP TABLE messages;