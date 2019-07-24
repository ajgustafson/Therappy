-- DROP DATABASE IF EXISTS therappy;
-- CREATE DATABASE IF NOT EXISTS therappy;
USE therappy;

DROP TABLE IF EXISTS user_exhibits_malady;
DROP TABLE IF EXISTS therapist_accepts_insurance;
DROP TABLE IF EXISTS therapist_has_qualification;
DROP TABLE IF EXISTS therapist_treats_malady;
DROP TABLE IF EXISTS user_rates_therapist;
DROP TABLE IF EXISTS user_matches_therapist;
DROP TABLE IF EXISTS therapist;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS qualification;
DROP TABLE IF EXISTS malady;
DROP TABLE IF EXISTS style;
DROP TABLE IF EXISTS insurance;

CREATE TABLE insurance (
	insurance_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE style (
	style_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE malady (
	malady_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE qualification (
	qualification_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE user (
	user_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    pwd VARCHAR(20) NOT NULL,
    gender ENUM('Female', 'Male'),
    zipcode CHAR(5) NOT NULL,
    dob DATE,
    insurance_id INT,
    style_pref INT,
	CONSTRAINT user_fk_insurance FOREIGN KEY (insurance_id) references insurance (insurance_id),
    CONSTRAINT pref_fk_style FOREIGN KEY (style_pref) references style (style_id)
);

CREATE TABLE therapist (
	therapist_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender ENUM('Female', 'Male') NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number CHAR(12) NOT NULL,
    dob DATE NOT NULL,
    zipcode CHAR(5) NOT NULL,
    cost_per_session INT NOT NULL,
    style_id INT NOT NULL,
    qualification_id INT NOT NULL,
    CONSTRAINT therapist_fk_style FOREIGN KEY (style_id) references style (style_id),
    CONSTRAINT therapist_fk_qualification FOREIGN KEY (qualification_id) references qualification (qualification_id)
);

CREATE TABLE user_matches_therapist (
	user_id INT NOT NULL,
    CONSTRAINT matches_fk_user FOREIGN KEY (user_id) references user (user_id),
    therapist_id INT NOT NULL,
    CONSTRAINT matches_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    strength INT NOT NULL,
    PRIMARY KEY (user_id, therapist_id)
);

CREATE TABLE user_rates_therapist (
	user_id INT NOT NULL,
    CONSTRAINT rates_fk_user FOREIGN KEY (user_id) references user (user_id),
    therapist_id INT NOT NULL,
    CONSTRAINT rates_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    rating INT NOT NULL,
    PRIMARY KEY (user_id, therapist_id)
);

CREATE TABLE therapist_treats_malady (
	therapist_id INT NOT NULL,
    CONSTRAINT treats_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    malady_id INT NOT NULL,
    CONSTRAINT treats_fk_malady FOREIGN KEY (malady_id) references malady (malady_id),
    PRIMARY KEY (therapist_id, malady_id)
);

CREATE TABLE therapist_has_qualification (
	therapist_id INT NOT NULL,
    CONSTRAINT hasqual_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    qualification_id INT NOT NULL,
    CONSTRAINT hasqual_fk_qualification FOREIGN KEY (qualification_id) references qualification (qualification_id),
    PRIMARY KEY (therapist_id, qualification_id)
);

CREATE TABLE therapist_accepts_insurance (
	therapist_id INT NOT NULL,
    CONSTRAINT accepts_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    insurance_id INT NOT NULL,
    CONSTRAINT accepts_fk_insurance FOREIGN KEY (insurance_id) references insurance (insurance_id),
    PRIMARY KEY (therapist_id, insurance_id)
);

CREATE TABLE user_exhibits_malady (
	user_id INT NOT NULL,
    CONSTRAINT exhibits_fk_user FOREIGN KEY (user_id) references user (user_id),
    malady_id INT NOT NULL,
    CONSTRAINT exibits_fk_malady FOREIGN KEY (malady_id) references malady (malady_id),
    PRIMARY KEY (user_id, malady_id)
);
