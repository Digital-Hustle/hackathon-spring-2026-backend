-- liquibase formatted sql

-- changeset dasemenov:260322-0204-insert-mock-messages-table
INSERT INTO messages (id, content, owned_by_user, created_at, workspace_id)
VALUES
-- Chat 1: Machine Learning Research
('00000000-0000-0000-0000-000000002001', 'What are the best practices for hyperparameter tuning?', true,
 '2026-03-01 09:05:00+00', '00000000-0000-0000-0000-000000000004'),
('00000000-0000-0000-0000-000000002002',
 'Try using Bayesian optimization or grid search with cross-validation. Also consider using Optuna.', false,
 '2026-03-01 09:10:00+00', '00000000-0000-0000-0000-000000000004'),
('00000000-0000-0000-0000-000000002003', 'Any recommended learning rate schedules?', true, '2026-03-05 14:35:00+00',
 '00000000-0000-0000-0000-000000000004'),
('00000000-0000-0000-0000-000000002004', 'Cosine annealing with warm restarts works well for many models.', false,
 '2026-03-05 14:40:00+00', '00000000-0000-0000-0000-000000000004'),

-- Chat 2: Дизайн мобильного приложения
('00000000-0000-0000-0000-000000002005', 'How to improve the onboarding flow?', true, '2026-03-10 11:20:00+00',
 '00000000-0000-0000-0000-000000000011'),
('00000000-0000-0000-0000-000000002006', 'Add a progress indicator and allow skipping optional steps.', false,
 '2026-03-10 11:25:00+00', '00000000-0000-0000-0000-000000000011'),
('00000000-0000-0000-0000-000000002007', 'Also consider using tooltips for first-time users.', false,
 '2026-03-10 11:27:00+00', '00000000-0000-0000-0000-000000000011'),

-- Chat 3: Backend Architecture
('00000000-0000-0000-0000-000000002008', 'Which database is better for high write load?', true,
 '2026-03-12 08:50:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002009', 'Cassandra or PostgreSQL with proper sharding?', false,
 '2026-03-12 08:52:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002010',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002041',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002042',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002043',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002044',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002045',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002046',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002047',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002048',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002049',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002050',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', true,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),
('00000000-0000-0000-0000-000000002051',
 'PostgreSQL can handle it if you design carefully, but for massive scale consider Cassandra or CockroachDB.', false,
 '2026-03-12 08:55:00+00', '00000000-0000-0000-0000-000000000012'),

-- Chat 4: Product Strategy Q2
('00000000-0000-0000-0000-000000002011', 'What features should we prioritize for Q2?', true, '2026-03-15 16:25:00+00',
 '00000000-0000-0000-0000-000000000001'),
('00000000-0000-0000-0000-000000002012', 'Based on user feedback, focus on AI-powered search and collaboration tools.',
 false, '2026-03-15 16:30:00+00', '00000000-0000-0000-0000-000000000001'),
('00000000-0000-0000-0000-000000002013', 'Let’s schedule a meeting with stakeholders to finalize.', false,
 '2026-03-15 16:35:00+00', '00000000-0000-0000-0000-000000000001'),

-- Chat 5: Кибербезопасность завода
('00000000-0000-0000-0000-000000002014', 'Any updates on the SCADA vulnerability assessment?', true,
 '2026-03-18 10:05:00+00', '00000000-0000-0000-0000-000000000015'),
('00000000-0000-0000-0000-000000002015', 'We found several outdated components; we need to patch immediately.', false,
 '2026-03-18 10:10:00+00', '00000000-0000-0000-0000-000000000015'),

-- Chat 6: Исследование рынка EdTech
('00000000-0000-0000-0000-000000002016', 'What are the key trends in EdTech for 2026?', true, '2026-03-20 13:35:00+00',
 '00000000-0000-0000-0000-000000000014'),
('00000000-0000-0000-0000-000000002017', 'Personalized learning with AI and VR/AR integration are growing fast.', false,
 '2026-03-20 13:40:00+00', '00000000-0000-0000-0000-000000000014'),

-- Chat 7: API Gateway Design
('00000000-0000-0000-0000-000000002018', 'Should we use Spring Cloud Gateway or Kong?', true, '2026-03-21 09:20:00+00',
 '00000000-0000-0000-0000-000000000013'),
('00000000-0000-0000-0000-000000002019',
 'Spring Cloud Gateway fits well if you are already in Spring ecosystem. Kong is more language-agnostic.', false,
 '2026-03-21 09:25:00+00', '00000000-0000-0000-0000-000000000013'),
('00000000-0000-0000-0000-000000002020', 'What about rate limiting and security?', true, '2026-03-21 09:28:00+00',
 '00000000-0000-0000-0000-000000000013'),
('00000000-0000-0000-0000-000000002021',
 'Both support OAuth2 and rate limiting; choose based on your team’s expertise.', false, '2026-03-21 09:32:00+00',
 '00000000-0000-0000-0000-000000000013');;
