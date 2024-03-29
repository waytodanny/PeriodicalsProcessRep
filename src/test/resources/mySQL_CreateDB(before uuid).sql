CREATE DATABASE periodicals
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE periodicals;

CREATE TABLE IF NOT EXISTS roles (
  id TINYINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) PRIMARY KEY,
  login NVARCHAR(50) NOT NULL UNIQUE,
  pass NVARCHAR(32) NOT NULL, /*for MD5 encrypted pass 32 bytes needed*/
  email VARCHAR(254) UNIQUE NOT NULL,
  role_id TINYINT UNSIGNED NOT NULL,
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS publishers (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name NVARCHAR(100) NOT NULL UNIQUE /*83 is max length name in the world*/
);

CREATE TABLE IF NOT EXISTS genres (
  id SMALLINT PRIMARY KEY AUTO_INCREMENT,
  name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS periodicals (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name NVARCHAR(100) NOT NULL,
  description NVARCHAR(1000),
  subscr_cost DECIMAL(5 , 2 ) UNSIGNED NOT NULL,
  issues_per_year SMALLINT UNSIGNED NOT NULL,
  is_limited BIT NOT NULL,
  genre_id SMALLINT UNSIGNED NOT NULL,
  publisher_id INT UNSIGNED NOT NULL,
  FOREIGN KEY (genre_id)
  REFERENCES genres (id),
  FOREIGN KEY (publisher_id)
  REFERENCES publishers (id)
);

CREATE TABLE IF NOT EXISTS periodical_issues (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  issue_no INT UNSIGNED NOT NULL UNIQUE,
  name NVARCHAR(100),
  publishing_date DATE NOT NULL,
  periodical_id INT UNSIGNED NOT NULL,

  FOREIGN KEY (periodical_id)
  REFERENCES periodicals (id)
);

/*Table of user subscriptions (many-to-many)*/
CREATE TABLE IF NOT EXISTS subscriptions (
  user_id CHAR(36) NOT NULL ,
  periodical_id INT UNSIGNED NOT NULL,

  PRIMARY KEY (user_id , periodical_id),

  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (periodical_id)
  REFERENCES periodicals (id)
);

CREATE TABLE IF NOT EXISTS payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  payment_sum DECIMAL(10,2) UNSIGNED NOT NULL,
  user_id CHAR(36) NOT NULL,

  FOREIGN KEY (user_id)
  REFERENCES users (id)
);

/*Table for payment periodicals (many-to-many)*/
CREATE TABLE IF NOT EXISTS payments_periodicals (
  payment_id BIGINT UNSIGNED NOT NULL,
  periodical_id INT UNSIGNED NOT NULL,

  PRIMARY KEY (payment_id , periodical_id),

  FOREIGN KEY (payment_id)
  REFERENCES payments (id),
  FOREIGN KEY (periodical_id)
  REFERENCES periodicals (id)
);