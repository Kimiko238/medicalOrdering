
INSERT INTO md_test_db.inspections (
    id,
    name,
    date,
    details,
    created_by,
    created_at,
    updated_by,
    updated_at,
    deleted_by,
    deleted_at
)
VALUES(
'id',
'テスト検査',
'2025-01-17 10:30:00',
'検査テストです',
'アシュ',
CURRENT_TIMESTAMP,
 null,
 null,
 null,
  null
);