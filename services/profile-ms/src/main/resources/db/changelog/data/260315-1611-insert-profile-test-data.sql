-- liquibase formatted sql

-- changeset dasemenov:260315-1611-insert-profile-test-data
INSERT INTO profile (id, name, surname, date_of_birth)
VALUES ('5bc388a6-f95d-459d-b5b5-51b63e01c533', 'Иван', 'Иванов', '2000-10-25'),
       ('8b62d449-4312-48ed-82ef-f9754ca4858b', 'Мария', 'Петрова', '1995-11-05'),
       ('21fabe35-28cc-41f5-be18-d08bc97cb584', 'Алексей', 'Сидоров', '2003-05-30'),
       ('8ef8da83-5137-48ed-aa72-b82ce10bfbd3', 'Елена', 'Кузнецова', '1997-04-05'),
       ('f73185e5-99a6-45d5-bc62-15172237615f', 'Дмитрий', 'Смирнов', '1990-05-04');
-- rollback DELETE FROM profile p WHERE p.id IN ('5bc388a6-f95d-459d-b5b5-51b63e01c533', '8b62d449-4312-48ed-82ef-f9754ca4858b',, '21fabe35-28cc-41f5-be18-d08bc97cb584', '8ef8da83-5137-48ed-aa72-b82ce10bfbd3', 'f73185e5-99a6-45d5-bc62-15172237615f');