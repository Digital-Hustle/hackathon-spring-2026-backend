-- liquibase formatted sql

-- changeset kiri4444:260322-0430-create-notes-table
CREATE TABLE IF NOT EXISTS notes
(
    id           UUID PRIMARY KEY,
    workspace_id UUID         NOT NULL,
    title        VARCHAR(150) NOT NULL,
    content_json JSONB        NOT NULL,
    CONSTRAINT fk_notes_workspace
        FOREIGN KEY (workspace_id) REFERENCES workspaces(id)
);