INSERT IGNORE INTO inspection_types (id, name, created_by, created_at)
VALUES
    ('1', '採血', 'テストユーザー', CURRENT_TIMESTAMP),
    ('2', '心電図', 'テストユーザー', CURRENT_TIMESTAMP),
    ('3', '胸部レントゲン', 'テストユーザー', CURRENT_TIMESTAMP),
    ('4', 'CT', 'テストユーザー', CURRENT_TIMESTAMP),
    ('5', 'MRI', 'テストユーザー', CURRENT_TIMESTAMP);