-- liquibase formatted sql

-- changeset dasemenov:260321-1308-create-vector-store-table
CREATE TABLE vector_store
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces (id) ON DELETE CASCADE,
    content      TEXT NOT NULL,
    metadata     JSONB,
    embedding    vector(1024)
);
-- rollback DROP TABLE vector_store;
