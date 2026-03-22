CREATE TABLE summaries (
    id UUID PRIMARY KEY,
    workspace_id UUID NOT NULL,
    content TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);