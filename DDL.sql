
-- 患者情報
CREATE TABLE patients (
id  VARCHAR(255) NOT NULL,
show_id  INT(6) NOT NULL auto_increment,
name VARCHAR(20) NOT NULL,
birthday  DATE NOT NULL,
gender  CHAR(1) NOT NULL,
created_by VARCHAR(255) NOT NULL,
created_at DATETIME NOT NULL,
updated_by VARCHAR(255),
updated_at DATETIME,
deleted_by VARCHAR(255),
deleted_at DATETIME,
PRIMARY KEY (id),
UNIQUE KEY (show_id)
);

--- usersテーブル
 CREATE TABLE md_db.users (
 id VARCHAR(255) NOT NULL,
 name VARCHAR(20) NOT NULL,
 birthDay DATE NOT NULL,
 gender CHAR(1) NOT NULL,
 pass VARCHAR(255) NOT NULL,
 created_by VARCHAR(255) NOT NULL,
 created_at DATETIME NOT NULL,
 updated_by VARCHAR(255),
 updated_at DATETIME,
 deleted_by VARCHAR(255),
 deleted_at DATETIME,
 PRIMARY KEY (id)
 );


--検査情報
CREATE TABLE md_db.inspections (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(20) NOT NULL,
    date DATETIME NOT NULL,
    details VARCHAR(100) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(255),
    updated_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_at DATETIME,
    PRIMARY KEY(id)    
);

--検査ステータス情報
CREATE TABLE md_db.inspection_statuses (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(10) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(255),
    updated_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_at DATETIME,
    PRIMARY KEY(id)
);

--オーダー情報
CREATE TABLE md_db.orders (
    id VARCHAR(255) NOT NULL,
    patient_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    inspection_id VARCHAR(255) NOT NULL,
    status_id VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(255),
    updated_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_at DATETIME,
    PRIMARY KEY(id),
    FOREIGN KEY (patient_id) REFRENCES patients(id),
    FOREIGN KEY (user_id) REFRENCES users(id),
    FOREIGN KEY (inspection_id) REFRENCESx inspections(id),
    FOREIGN KEY (status_id) REFRENCES inspection_statuses(id)
);
