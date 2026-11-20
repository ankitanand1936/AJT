-- =====================================================================
-- LearnForge - database script (MySQL 8)
-- Creates the database "learnforge" with EXACTLY seven tables:
--   roles, users, modules, lessons, questions, quiz_results, certificates
--
-- Running this script is optional: with spring.jpa.hibernate.ddl-auto=update
-- the application creates the same tables itself. The default admin account and the
-- sample Java content are inserted by the application on first start (passwords are
-- BCrypt-hashed in Java, so they are not inserted here).
-- =====================================================================

CREATE DATABASE IF NOT EXISTS learnforge
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE learnforge;

-- 1. roles  (roles -> users : one-to-many)
CREATE TABLE IF NOT EXISTS roles (
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    name  VARCHAR(30)  NOT NULL,                     -- ADMIN or STUDENT
    PRIMARY KEY (id),
    CONSTRAINT uk_roles_name UNIQUE (name)
) ENGINE = InnoDB;

-- 2. users
CREATE TABLE IF NOT EXISTS users (
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    full_name  VARCHAR(100)  NOT NULL,
    email      VARCHAR(150)  NOT NULL,
    password   VARCHAR(255)  NOT NULL,               -- BCrypt hash
    role_id    BIGINT        NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE = InnoDB;

-- 3. modules  (modules -> lessons : one-to-many)
CREATE TABLE IF NOT EXISTS modules (
    id           BIGINT        NOT NULL AUTO_INCREMENT,
    title        VARCHAR(150)  NOT NULL,
    description  VARCHAR(500)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_modules_title UNIQUE (title)
) ENGINE = InnoDB;

-- 4. lessons  (lessons -> questions and lessons -> quiz_results : one-to-many)
CREATE TABLE IF NOT EXISTS lessons (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    module_id     BIGINT        NOT NULL,
    title         VARCHAR(150)  NOT NULL,
    content       TEXT          NOT NULL,
    lesson_order  INT           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_lesson_module_title UNIQUE (module_id, title),
    CONSTRAINT fk_lessons_module FOREIGN KEY (module_id) REFERENCES modules (id)
) ENGINE = InnoDB;

-- 5. questions  (the quiz of a lesson = its questions; options A-D are columns)
CREATE TABLE IF NOT EXISTS questions (
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    lesson_id       BIGINT        NOT NULL,
    question_text   VARCHAR(500)  NOT NULL,
    option_a        VARCHAR(255)  NOT NULL,
    option_b        VARCHAR(255)  NOT NULL,
    option_c        VARCHAR(255)  NOT NULL,
    option_d        VARCHAR(255)  NOT NULL,
    correct_option  CHAR(1)       NOT NULL,          -- A, B, C or D
    PRIMARY KEY (id),
    CONSTRAINT fk_questions_lesson FOREIGN KEY (lesson_id) REFERENCES lessons (id),
    CONSTRAINT chk_questions_correct CHECK (correct_option IN ('A', 'B', 'C', 'D'))
) ENGINE = InnoDB;

-- 6. quiz_results  (users -> quiz_results and lessons -> quiz_results : one-to-many)
--    One row per attempt. A lesson is completed when a row with passed = TRUE exists.
CREATE TABLE IF NOT EXISTS quiz_results (
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    user_id          BIGINT      NOT NULL,
    lesson_id        BIGINT      NOT NULL,
    score            INT         NOT NULL,
    total_questions  INT         NOT NULL,
    passed           BIT(1)      NOT NULL,
    attempted_at     DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_results_user   FOREIGN KEY (user_id)   REFERENCES users (id),
    CONSTRAINT fk_results_lesson FOREIGN KEY (lesson_id) REFERENCES lessons (id)
) ENGINE = InnoDB;

-- 7. certificates  (users -> certificates : one-to-one in practice, enforced by UNIQUE user_id)
CREATE TABLE IF NOT EXISTS certificates (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    user_id           BIGINT       NOT NULL,
    certificate_code  VARCHAR(40)  NOT NULL,
    issued_at         DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_certificates_user UNIQUE (user_id),
    CONSTRAINT uk_certificates_code UNIQUE (certificate_code),
    CONSTRAINT fk_certificates_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB;
