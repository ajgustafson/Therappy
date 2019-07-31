use Therappy;

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

select *
from user;

create procedure findSimilarUsers
(
	in user_id_param int  -- params go here, separated by commas
)

begin

	declare gender_var ENUM('F', 'M');
    declare gender_pref_var ENUM('F', 'M');
    declare age_var int;
    declare style_pref_var int; -- variable declarations, ending each lline with semicolons
    declare qualification_pref_var int;
    
    -- store in a view?
    select
		gender,
		gender_pref,
        year(now()) - year(dob),
        style_pref,
        qualification_pref,
	into
		gender_var,
		gender_pref_var,
		age_var,
		style_pref_var,
		qualification_pref_var
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