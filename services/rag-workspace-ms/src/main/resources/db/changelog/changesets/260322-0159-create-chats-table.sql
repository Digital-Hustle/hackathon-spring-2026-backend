-- liquibase formatted sql

-- changeset dasemenov:260322-0159-create-chats-table
CREATE TABLE IF NOT EXISTS chats
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces (id) ON DELETE CASCADE,
    created_at   TIMESTAMP        DEFAULT NOW()
);
-- rollback DROP TABLE chats;
