INSERT IGNORE INTO md_test_db.users (
  id,
  name,
  birthday,
  gender,
  pass,
  created_by,
  created_at,
  updated_by,
  updated_at,
  deleted_by,
  deleted_at
) VALUES (
    'addId',
    'たろう',
    '2024-12-10',
    '1',
    'word',
    'アシュ',
    NOW(),
    null,
    null,
    null,
    null
);