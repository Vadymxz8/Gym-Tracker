INSERT INTO users (id, name, email, password)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'admin',
    'admin@example.com',
    'admin123'
)
ON CONFLICT (id) DO NOTHING;