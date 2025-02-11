INSERT IGNORE INTO inspection_types(
id,
name,
created_by,
created_at
)
SELECT '1','採血','テストユーザー',CURRENT_TIMESTAMP
WHERE NOT EXISTS(SELECT 1 FROM inspection_types WHERE id = '1');

INSERT IGNORE INTO inspection_types(
id,
name,
created_by,
created_at
)
SELECT '2','心電図','テストユーザー',CURRENT_TIMESTAMP
WHERE NOT EXISTS(SELECT 1 FROM inspection_types WHERE id = '2');

INSERT IGNORE INTO inspection_types(
id,
name,
created_by,
created_at
)
SELECT '3','胸部レントゲン','テストユーザー',CURRENT_TIMESTAMP
WHERE NOT EXISTS(SELECT 1 FROM inspection_types WHERE id = '3');

INSERT IGNORE INTO inspection_types(
id,
name,
created_by,
created_at
)
SELECT '4','CT','テストユーザー',CURRENT_TIMESTAMP
WHERE NOT EXISTS(SELECT 1 FROM inspection_types WHERE id = '4');

INSERT IGNORE INTO inspection_types(
id,
name,
created_by,
created_at
)
SELECT '5','MRI','テストユーザー',CURRENT_TIMESTAMP
WHERE NOT EXISTS(SELECT 1 FROM inspection_types WHERE id = '5');

