-- 患者情報
CREATE TABLE IF NOT EXISTS patients (
id  VARCHAR(255) NOT NULL,
show_id  INT NOT NULL auto_increment,
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
 CREATE TABLE IF NOT EXISTS users (
 id VARCHAR(255) NOT NULL,
 name VARCHAR(20) NOT NULL,
 birthday DATE NOT NULL,
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


--検査の種類
CREATE TABLE IF NOT EXISTS inspection_types (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(20) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(255),
    updated_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_at DATETIME,
    PRIMARY KEY(id)
);


--オーダー情報
CREATE TABLE IF NOT EXISTS inspection_orders (
    id VARCHAR(255) NOT NULL,
    patient_show_id INT NOT NULL,
    inspection_id VARCHAR(255) NOT NULL,
    status VARCHAR(5) NOT NULL,
    inspection_details VARCHAR(255) NOT NULL,
    inspection_date DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by VARCHAR(255),
    updated_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_at DATETIME,
    PRIMARY KEY(id),
    FOREIGN KEY (patient_show_id) REFERENCES patients(show_id),
    FOREIGN KEY (inspection_id) REFERENCES inspection_types(id)
);
