CREATE TABLE departments (
                             id BIGINT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE
);
create sequence departments_id_seq;

CREATE TABLE users (
                       id BIGINT  PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       department_id BIGINT,
                       FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

create sequence users_id_seq;
