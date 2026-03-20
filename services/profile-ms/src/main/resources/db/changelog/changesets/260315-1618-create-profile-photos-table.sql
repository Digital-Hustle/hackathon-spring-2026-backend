-- liquibase formatted sql

-- changeset dasemenov:260217-1211-create-place-photos-table
CREATE TABLE IF NOT EXISTS photos
(
    id                 UUID PRIMARY KEY,
    profile_id         UUID         NOT NULL REFERENCES profile (id),
    original_file_name VARCHAR(255) NOT NULL,
    extension          VARCHAR(10)  NOT NULL,
    file_size          INTEGER      NOT NULL,
    content_type       VARCHAR(100),
    uploaded_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- rollback DROP TABLE place_photos;
