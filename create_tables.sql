DROP DATABASE IF EXISTS therappy;
CREATE DATABASE IF NOT EXISTS therappy;
USE therappy;

DROP TABLE IF EXISTS user_exhibits_malady;
DROP TABLE IF EXISTS therapist_accepts_insurance;
DROP TABLE IF EXISTS therapist_treats_malady;
DROP TABLE IF EXISTS user_rates_therapist;
DROP TABLE IF EXISTS user_matches_therapist;
DROP TABLE IF EXISTS user_makes_choices;
DROP TABLE IF EXISTS therapist;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS qualification;
DROP TABLE IF EXISTS malady;
DROP TABLE IF EXISTS style;
DROP TABLE IF EXISTS insurance;
DROP TABLE IF EXISTS choice;

-- This table holds all the options the user can choose for the
-- style matching portion of the account creation quiz
CREATE TABLE choice (
	choice_id INT PRIMARY KEY,
        content VARCHAR(200) NOT NULL UNIQUE,
	value INT
);

-- This table holds information for insurance companies
CREATE TABLE insurance (
	insurance_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- This table holds information for the different styles of therapy
CREATE TABLE style (
	style_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- This table holds information for different maladies (Anxiety, Depression, etc.)
CREATE TABLE malady (
	malady_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- This table holds the qualfications (MA, PhD, PsyD)
CREATE TABLE qualification (
	qualification_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- This table holds the information about the user
-- and has several foreign keys to tables such as insurance, qualification, and style
CREATE TABLE user (
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    pwd VARCHAR(20) NOT NULL,
    dob DATE,
    gender ENUM('F', 'M'),
    email VARCHAR(100) NOT NULL UNIQUE,
    zipcode CHAR(5) NOT NULL,
    insurance_id INT,
    CONSTRAINT user_fk_insurance FOREIGN KEY (insurance_id) references insurance (insurance_id),
    max_distance INT NOT NULL,
    gender_pref ENUM('F', 'M'),
    max_cost INT,
    qualification_pref INT,
    CONSTRAINT user_fk_qualification FOREIGN KEY (qualification_pref) references qualification (qualification_id),
    needs_insurance boolean NOT NULL,
    style_pref INT,
    CONSTRAINT user_fk_style FOREIGN KEY (style_pref) references style (style_id)
);

-- This table holds the information about the therapist
-- and has several foreign keys to tables such as qualification, and style
CREATE TABLE therapist (
	therapist_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('F', 'M') NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number CHAR(12) NOT NULL,
    zipcode CHAR(5) NOT NULL,
    cost_per_session INT NOT NULL,
    style_id INT NOT NULL,
    CONSTRAINT therapist_fk_style FOREIGN KEY (style_id) references style (style_id),
    qualification_id INT NOT NULL,
    CONSTRAINT therapist_fk_qualification FOREIGN KEY (qualification_id) references qualification (qualification_id)
);

-- This table holds the user-therapist matches
-- in addition to the strength of the match
CREATE TABLE user_matches_therapist (
	user_id INT NOT NULL,
    CONSTRAINT matches_fk_user FOREIGN KEY (user_id) references user (user_id),
    therapist_id INT NOT NULL,
    CONSTRAINT matches_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    strength INT NOT NULL,
    PRIMARY KEY (user_id, therapist_id)
);

-- Holds the user, the rating the user gave the therapist, and the therapist themselves
CREATE TABLE user_rates_therapist (
	user_id INT NOT NULL,
    CONSTRAINT rates_fk_user FOREIGN KEY (user_id) references user (user_id),
    therapist_id INT NOT NULL,
    CONSTRAINT rates_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    rating INT NOT NULL
);

-- Holds the therapist and the malady they treat.
CREATE TABLE therapist_treats_malady (
	therapist_id INT NOT NULL,
    CONSTRAINT treats_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    malady_id INT NOT NULL,
    CONSTRAINT treats_fk_malady FOREIGN KEY (malady_id) references malady (malady_id),
    PRIMARY KEY (therapist_id, malady_id)
);

-- HOlds the therapist and the insurance they accept
CREATE TABLE therapist_accepts_insurance (
	therapist_id INT NOT NULL,
    CONSTRAINT accepts_fk_therapist FOREIGN KEY (therapist_id) references therapist (therapist_id),
    insurance_id INT NOT NULL,
    CONSTRAINT accepts_fk_insurance FOREIGN KEY (insurance_id) references insurance (insurance_id),
    PRIMARY KEY (therapist_id, insurance_id)
);

-- Holds the user and the maladies (or malady) they suffer from
CREATE TABLE user_exhibits_malady (
	user_id INT NOT NULL,
    CONSTRAINT exhibits_fk_user FOREIGN KEY (user_id) references user (user_id),
    malady_id INT NOT NULL,
    CONSTRAINT exibits_fk_malady FOREIGN KEY (malady_id) references malady (malady_id)
   
);

-- Stores the user's choices in the style portion of the new-account quiz
CREATE TABLE user_makes_choices(
	user_id INT NOT NULL,
    CONSTRAINT answer_fk_user FOREIGN KEY (user_id) references user (user_id),
    choice_id INT NOT NULL,
    CONSTRAINT answer_fk_choice FOREIGN KEY (choice_id) references choice(choice_id)
    
);
