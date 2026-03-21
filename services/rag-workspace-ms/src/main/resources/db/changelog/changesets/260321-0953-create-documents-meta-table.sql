-- liquibase formatted sql

-- changeset dasemenov:260321-0953-create-documents-meta-table
CREATE TABLE IF NOT EXISTS documents_meta
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_name VARCHAR(255) NOT NULL,
    content_type  VARCHAR(100) NOT NULL,
    extension     VARCHAR(20)  NOT NULL,
    size          BIGINT       NOT NULL,
    status        VARCHAR(20)  NOT NULL,
    active        BOOLEAN          DEFAULT TRUE,
    error_message TEXT,
    workspace_id  UUID         NOT NULL REFERENCES workspaces (id) ON DELETE CASCADE,
    created_at    TIMESTAMP        DEFAULT NOW()
);
-- rollback DROP TABLE documents_meta;
