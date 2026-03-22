CREATE TABLE summaries_document_ids (
    summary_id UUID NOT NULL REFERENCES summaries(id) ON DELETE CASCADE,
    document_id UUID NOT NULL,
    PRIMARY KEY (summary_id, document_id)
);