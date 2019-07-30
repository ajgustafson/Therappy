DROP DATABASE IF EXISTS therappy;
CREATE DATABASE IF NOT EXISTS therappy;
USE therappy;

DROP TABLE IF EXISTS user_exhibits_malady;
DROP TABLE IF EXISTS therapist_accepts_insurance;
DROP TABLE IF EXISTS therapist_treats_malady;
DROP TABLE IF EXISTS user_rates_therapist;
DROP TABLE IF EXISTS user_matches_therapist;
DROP TABLE IF EXISTS user_answers_questions;
DROP TABLE IF EXISTS therapist;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS qualification;
DROP TABLE IF EXISTS malady;
DROP TABLE IF EXISTS style;
DROP TABLE IF EXISTS insurance;
DROP TABLE IF EXISTS question;


CREATE TABLE question (
	question_id INT PRIMARY KEY,
    content VARCHAR(200) NOT NULL UNIQUE
);

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
    gender ENUM('F', 'M'),
    zipcode CHAR(5) NOT NULL,
    dob DATE,
    insurance_id INT,
    CONSTRAINT user_fk_insurance FOREIGN KEY (insurance_id) references insurance (insurance_id),
    max_distance INT NOT NULL,
    gender_pref ENUM('F', 'M'),
    qualification_pref INT,
    CONSTRAINT user_fk_qualification FOREIGN KEY (qualification_pref) references qualification (qualification_id),
    needs_insurance boolean NOT NULL,
    style_pref INT,
    CONSTRAINT user_fk_style FOREIGN KEY (style_pref) references style (style_id)
);

CREATE TABLE therapist (
	therapist_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender ENUM('F', 'M') NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number CHAR(12) NOT NULL,
    dob DATE NOT NULL,
    zipcode CHAR(5) NOT NULL,
    cost_per_session INT NOT NULL,
    style_id INT NOT NULL,
    CONSTRAINT therapist_fk_style FOREIGN KEY (style_id) references style (style_id),
    qualification_id INT NOT NULL,
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

CREATE TABLE user_answers_questions(
	user_id INT NOT NULL,
    CONSTRAINT answer_fk_user FOREIGN KEY (user_id) references user (user_id),
    question_id INT NOT NULL,
    CONSTRAINT answer_fk_question FOREIGN KEY (question_id) references question (question_id),
    PRIMARY KEY (user_id, question_id)
);


delimiter //
-- This procedure adds a new user to the databse AFTER they have completed the questionaire
create procedure addUser
(
	in first_name_param VARCHAR(50),
    in last_name_param VARCHAR(50),
    in username_param VARCHAR(50),
    in email_param VARCHAR(100),
    in pwd_param VARCHAR(20),
    in gender_param ENUM('F', 'M'),
    in zipcode_param CHAR(5),
    in dob_param DATE,
    in insurance_param VARCHAR(100),
    in max_distance_param INT,
    in gender_pref_param ENUM('F', 'M'),
    in qualification_pref_param VARCHAR(100),
    in needs_insurance_param boolean,
    in question_1_param int,
    in question_2_param int,
    in question_3_param int,
    in question_4_param int,
    in question_5_param int
)

begin
	-- variable declarations
    declare style_pref_var int;
    declare qualification_pref_id_var int;
    declare insurance_id_var int;
    declare user_id_var int;
    
end //
delimiter ;

-- This procedure finds all similar users to the current user
delimiter //

create procedure findSimilarUsers
(
	in user_id_param int  -- params go here, separated by commas
)

begin

    declare style_pref_var int; -- variable declarations, ending each lline with semicolons
    declare max_distance_pref_var int;
    declare gender_pref_var ENUM('F', 'M');
    declare qualification_pref_var int;
    declare zipcode_var varchar(5);
    
    -- store in a view?
    select
		style_pref,
        max_distance,
        gender_pref,
        qualification_pref,
        zip_code
	into
		style_pref_var,
		max_distance_pref_var,
		gender_pref_var,
		qualification_pref_var,
		zipcode_var
	from user
    where user_id = user_id_param;
    
    
end //
delimiter ;


-- This procedure finds all user/therapist matches
delimiter //

create procedure findMatchingTherapists
(
	in user_id_param int  -- params go here, separated by commas
)

begin

    declare style_pref_var int; -- variable declarations, ending each lline with semicolons
    declare max_distance_pref_var int;
    declare gender_pref_var ENUM('F', 'M');
    declare qualification_pref_var int;
    declare zipcode_var varchar(5);
    
    -- store in a view?
    select
		style_pref,
        max_distance,
        gender_pref,
        qualification_pref,
        zip_code
	into
		style_pref_var,
		max_distance_pref_var,
		gender_pref_var,
		qualification_pref_var,
		zipcode_var
	from user
    where user_id = user_id_param;
    
end //
delimiter ;

-- This procedure filters the list of user/therapist matches using 
-- similar users then returns a list of the top 5
delimiter //

create procedure filterMatchingTherapists 
(
	in user_id_param int  -- params go here, separated by commas
)

begin

    declare var_name int; -- variable declarations, ending each lline with semicolons
    
end //
delimiter ;