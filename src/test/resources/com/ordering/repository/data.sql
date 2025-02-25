
--患者情報
INSERT IGNORE INTO md_test_db.patients (
id,
show_id,
name,
birthday,
gender,
created_by,
created_at,
updated_by,
updated_at,
deleted_by,
deleted_at
)
VALUES (
"patientId",
100,
"テスト 花子",
"2000-05-18",
'2',
"テスト ユーザー",
NOW(),
null,
null,
null,
null
);


INSERT IGNORE INTO users (
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
)
VALUES (
"userId",
"テスト ユーザー",
"2023-10-07",
'1',
"pass",
"テスト ユーザー",
NOW(),
null,
null,
null,
null
);

--検査の種類
INSERT IGNORE INTO inspection_types (id, name, created_by, created_at)
VALUES
    ('1', '採血', 'テストユーザー', CURRENT_TIMESTAMP),
    ('2', '心電図', 'テストユーザー', CURRENT_TIMESTAMP),
    ('3', '胸部レントゲン', 'テストユーザー', CURRENT_TIMESTAMP),
    ('4', 'CT', 'テストユーザー', CURRENT_TIMESTAMP),
    ('5', 'MRI', 'テストユーザー', CURRENT_TIMESTAMP);




--検査依頼情報
INSERT  IGNORE INTO md_test_db.inspection_orders (
    id,
    inspection_id,
    patient_show_id,
    status,
    inspection_details,
    inspection_date,
    created_by,
    created_at,
    updated_by,
    updated_at,
    deleted_by,
    deleted_at
)
VALUES(
  "orderId",
  "1",
  100,
  "未実施",
  "テストテスト",
  "2021-05-10 12:00:00",
  "アシュ",
  NOW(),
  null,
  null,
  null,
  null
);