--DROP DATABASE IF EXISTS kingverse_test;
--CREATE DATABASE kingverse_test CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
--USE kingverse_test;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS character_connection;
DROP TABLE IF EXISTS character_book;
DROP TABLE IF EXISTS character_entity;
DROP TABLE IF EXISTS book;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE book (
  book_id         INT PRIMARY KEY AUTO_INCREMENT,
  title           VARCHAR(200) NOT NULL,
  year_published  INT,
  setting         VARCHAR(100),
  series          VARCHAR(100),
  summary         TEXT
) ENGINE=InnoDB;
CREATE INDEX idx_book_title           ON book(title);
CREATE INDEX idx_book_year_published  ON book(year_published);
CREATE INDEX idx_book_setting         ON book(setting);
CREATE INDEX idx_book_series          ON book(series);

CREATE TABLE character_entity (
  character_id     INT PRIMARY KEY AUTO_INCREMENT,
  name             VARCHAR(150) NOT NULL,
  description      TEXT,
  first_appearance INT,
  image_url        VARCHAR(500),
  CONSTRAINT fk_character_first_appearance
    FOREIGN KEY (first_appearance) REFERENCES book(book_id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;
CREATE INDEX idx_character_name ON character_entity(name);

CREATE TABLE character_book (
  id            INT PRIMARY KEY AUTO_INCREMENT,
  character_id  INT NOT NULL,
  book_id       INT NOT NULL,
  role          ENUM('Main','Supporting','Cameo','Mention') DEFAULT 'Supporting',
  CONSTRAINT fk_cb_character FOREIGN KEY (character_id)
    REFERENCES character_entity(character_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_cb_book FOREIGN KEY (book_id)
    REFERENCES book(book_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT uq_cb UNIQUE (character_id, book_id)
) ENGINE=InnoDB;
CREATE INDEX idx_cb_book_role      ON character_book(book_id, role);
CREATE INDEX idx_cb_character_role ON character_book(character_id, role);

CREATE TABLE character_connection (
  connection_id   INT PRIMARY KEY AUTO_INCREMENT,
  subject_id      INT NOT NULL,
  object_id       INT NOT NULL,
  connection_type VARCHAR(40) NOT NULL,
  book_context_id INT NULL,
  note            VARCHAR(255),
  CONSTRAINT fk_cc_subject FOREIGN KEY (subject_id) REFERENCES character_entity(character_id) ON DELETE CASCADE,
  CONSTRAINT fk_cc_object  FOREIGN KEY (object_id)  REFERENCES character_entity(character_id) ON DELETE CASCADE,
  CONSTRAINT fk_cc_book    FOREIGN KEY (book_context_id) REFERENCES book(book_id) ON DELETE SET NULL,
  CONSTRAINT chk_cc_distinct CHECK (subject_id <> object_id)
) ENGINE=InnoDB;
CREATE INDEX idx_cc_subject ON character_connection(subject_id);
CREATE INDEX idx_cc_object  ON character_connection(object_id);
CREATE INDEX idx_cc_type    ON character_connection(connection_type);

CREATE TABLE user_account (
  user_id       INT PRIMARY KEY AUTO_INCREMENT,
  username      VARCHAR(60) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  enabled       TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE role (
  role_id INT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(32) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE user_role (
  user_role_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id      INT NOT NULL,
  role_id      INT NOT NULL,
  CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE,
  CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE,
  CONSTRAINT uq_user_role UNIQUE (user_id, role_id)
) ENGINE=InnoDB;
