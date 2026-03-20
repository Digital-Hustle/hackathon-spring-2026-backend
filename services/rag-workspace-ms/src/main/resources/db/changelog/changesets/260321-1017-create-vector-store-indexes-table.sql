-- liquibase formatted sql

-- changeset dasemenov:260321-1017-create-vector-store-indexes-table
CREATE INDEX ON vector_store USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX idx_vector_store_workspace ON vector_store (workspace_id);
-- rollback DROP TABLE idx_vector_store_workspace;
