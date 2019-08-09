use therappy;

-- A stored procedure to insert some information into the User table after the user completes the questionaire.  
-- Inserts information that does not depend on any other tables in the User table.
-- Information that depends on other tables is inserted as null (and will be updated via another procedure)

drop procedure if exists insert_user_basics;

delimiter //
create procedure insert_user_basics
(
    in first_name varchar(255),
    in last_name varchar(255),
    in username varchar(255),
    in pword varchar(255),
    in date_of_birth date,
    in gender varchar(255),
	in email varchar(255),
    in zipcode char(5),
    in max_distance int,
    in gender_pref varchar(255),
    in max_cost int,
    in needs_insurance tinyint
)
begin

    insert into user (first_name, last_name, username, pwd, dob, gender, email, zipcode, insurance_id, max_distance, gender_pref, max_cost, qualification_pref, needs_insurance, style_pref) values(first_name, last_name, username, pword, date_of_birth, gender, email, zipcode, null, max_distance, gender_pref, max_cost, null, needs_insurance, null);

end //
delimiter ;

-- A stored procedure to update (as it was initially set to null) the user's insurance info in the User table.  
-- Creates a new entry in the Insurance table if insurance does noe exist yet
drop procedure if exists insert_user_insurance;

delimiter //
create procedure insert_user_insurance
(
	in email_param varchar(255),
    in insurance_param varchar(255)
)
begin
	
    declare insurance_id_var int;
        
if insurance_param in (select name from insurance) then
	 select insurance_id into insurance_id_var from insurance where name = insurance_param;
else
	insert into insurance (name) values (insurance_param);
    select insurance_id into insurance_id_var from insurance where name = insurance_param;
end if;
    
update user
set insurance_id = insurance_id_var
where email = email_param;

end //
delimiter ;

-- Procedure to update the user's qualification preference (as it was initially set to null) in the User table
-- If the qual_param does not match exactly an entry in the Qualification table, qaulification_pref will remain as null
-- in the User table
drop procedure if exists insert_user_qual_pref;

delimiter //
create procedure insert_user_qual_pref
(
	in email_param varchar(255),
    in qual_param varchar(255)
)
begin
    declare qual_id_var int;
    set qual_id_var = null;
        
if qual_param in (select name from qualification) then
	 select qualification_id into qual_id_var from qualification where name = qual_param;
end if;
    
update user
set qualification_pref  = qual_id_var
where email = email_param;

end //
delimiter ;

-- Procedure to insert user's maladies into the user_exhibits_malady table.  Malady_param must match exactly 
-- to an entry in the malady table.
drop procedure if exists insert_user_malady;

delimiter //
create procedure insert_user_malady
(
	in email_param varchar(255),
    in malady_param varchar(255)
)
begin
    declare user_id_var int;
    declare malady_id_var int;
        
select user_id into user_id_var from user where email = email_param;

if malady_param in (select name from malady) then
	select malady_id into malady_id_var from malady where name = malady_param;
    insert into user_exhibits_malady (user_id, malady_id) values (user_id_var, malady_id_var);
end if;

end //
delimiter ;


-- Procedure to insert the user's responses to the 5 questions at the end of the survey related to style preference.
drop procedure if exists insert_response;

delimiter //
create procedure insert_response
(
	in email_param varchar(255),
    in choice_id_param int
)
begin
    declare user_id_var int;
        
select user_id into user_id_var from user where email = email_param;

insert into user_makes_choices(user_id, choice_id) values (user_id_var, choice_id_param);
end //
delimiter ;

-- Procedure that should be called after the user's responses to the style pref questions have been inserted into the table
-- Determines the user's style pref and inserts this entry into the 'style_pref' column in the User table
drop procedure if exists update_style;

delimiter //
create procedure update_style
(
	in email_param varchar(255)
)
begin
    declare user_id_var int;
    declare total_sum_var int;
    declare style_id_var int;
        
	select user_id into user_id_var from user where email = email_param;

	-- Get the total score from the user's answers in the style section of the quiz
	select SUM(value) 
    into total_sum_var 
    from user_makes_choices u left join choice c on (u.choice_id = c.choice_id) 
    where user_id = user_id_var; 

	-- If the sum of the user's answers was positive,
    -- assign to them behavioral style
	if (total_sum_var > 0) then
		select style_id into style_id_var from style where name = 'behavioral' limit 1;
		update user
		set style_pref = style_id_var
		where email = email_param;
	end if;
    
    -- else, assign procedural
	if (total_sum_var < 0) then
		select style_id into style_id_var from style where name = 'procedural' limit 1;
		update user
		set style_pref = style_id_var
		where email = email_param;
	end if;
	
end //
delimiter ;

-- Procedure to insert a user's rating of a therapist into the 'user_rates_therapist table'
drop procedure if exists insert_therapist_rating;

delimiter //
create procedure insert_therapist_rating
(
	in email_param varchar(255),
    in therapist_id_param int,
    in rating_param int
)
begin
    declare user_id_var int;
        
	select user_id 
    into user_id_var 
    from user 
    where email = email_param;

	insert into user_rates_therapist(user_id, therapist_id, rating) 
    values (user_id_var, therapist_id_param, rating_param);
    
end //
delimiter ;


drop procedure if exists findMatchingTherapists;

-- This procedure finds all user/therapist matches based on the following criteria:
-- +1 if the user's style preference matches the therapit's style
-- +1 if the user's gender preference matches the therapit's gender
-- +1 if the user's qualification preference matches the therapit's qualification
-- +1 if the therapists price is less than the user's maxmimum cost
-- +1 if the user needs to use insurance and the therapist takes their insurance
-- +1 if the therapist specializes in the user's malady
-- +1-5 based on the avg similar user score of a therapist (1 being therapist had a one rating and 5 being they had a five rating)

delimiter //

create procedure findMatchingTherapists
(
	in user_email_param varchar(255) -- params go here, separated by commas
)

begin
	
    declare user_id_var int; -- variable declarations, ending each line with semicolons
    declare style_pref_var int; 
    declare gender_pref_var ENUM('F', 'M');
    declare qualification_pref_var int;
    declare max_cost_var int;
    declare needs_insurance_var tinyint;
    declare malady_id_var int;
    declare gender_var ENUM('F', 'M');
    declare age_var int;

    
    select
		user_id,
		style_pref,
        gender_pref,
        qualification_pref,
        max_cost,
        needs_insurance,
        gender,
        year(now()) - year(dob)
	into
		user_id_var,
		style_pref_var,
		gender_pref_var,
		qualification_pref_var,
        max_cost_var,
        needs_insurance_var,
        gender_var,
        age_var
	from user
    where email = user_email_param;
    
	drop temporary table if exists similar_users;
    drop temporary table if exists therapist_scores;
    
    -- creates a table of similar users to be used when calculating avg similar user rating per therapist
	create temporary table similar_users as
	select user_id
    from (
		select
			user_id, first_name, last_name,
			gender = gender_var as gender_match,
			gender_pref = gender_pref_var as gender_pref_match,
            
            -- calculates the age: absolute value(user's age - similar user's age)
			abs(CAST(age_var AS SIGNED) - CAST(year(now()) - year(dob) as signed)) <= 5 as age_match,
            
			style_pref_var = style_pref as style_match,
			qualification_pref_var = qualification_pref as qualification_match,
            
            -- checking if the user exhibits similar maladies
			user_id in (
				select uem.user_id from user_exhibits_malady um 
				join user_exhibits_malady uem on (um.malady_id = uem.malady_id and um.user_id != uem.user_id)
				where uem.user_id = user_id_var
				) as malady_match
		from user
		where user_id_var != user_id
		group by user_id
		having gender_match + gender_pref_match + age_match + style_match + qualification_match >= 4
	)tmpSimilar;
    
    -- creates a table of match score for all therapists for a given user
	create temporary table therapist_scores as
    select 
		therapist_id,
        first_name,
        last_name,
        dob,
        gender,
        email,
        phone_number,
        zipcode,
        cost_per_session,
        style_id,
        qualification_id,
		gender_pref_match,
        style_match,
        qualification_match,
        cost_match,
        malady_match,
        insurance_match
    from (
	select
		*,
        -- finds if user gender_pref matches therapist gender (1 if matches, 0 if not)
		gender = gender_pref_var as gender_pref_match,
        
        -- finds if user style_pref matches therapist style (1 if matches, 0 if not)
		style_id = style_pref_var as style_match,
        
        -- finds if user qualification_pref matches therapist qualification (1 if matches, 0 if not)
		qualification_id = qualification_pref_var as qualification_match,
	
        -- finds if user max cost is less than or equal to therapist cost (1 if matches, 0 if not)
        cost_per_session <= max_cost_var as cost_match,
        
        -- finds if threapist specializes in user's malady (1 if matches, 0 if not)
        therapist_id in (select 
				therapist_id
				from user left join user_exhibits_malady using (user_id) 
				left join therapist_treats_malady using (malady_id)
				where malady_id = malady_id_var
				group by therapist_id) as malady_match,

		-- checks if user needs therapist to take insurance
        -- if yes then checks for insurance match
		case when needs_insurance_var = 1
			then therapist_id in (
				select 
				t.therapist_id
				from therapist t left join therapist_accepts_insurance tai using (therapist_id)
				where tai.insurance_id in (
					select 
					i.insurance_id
					from user left join insurance i using (insurance_id)
					where user_id = user_id_var))
			else 1
		end as insurance_match
	from therapist
	group by therapist_id
    )tmpTherapist;
    
    -- This query joins therapist scores, user ratings, and similar users to get the final match score of every therapist by adding
    -- the therapist scores to the avg similar user rating score
    -- The result is sent to java where it will then be further filtered
		select ts.therapist_id,
			ts.first_name,
			ts.last_name,
			ts.dob,
			ts.gender,
			ts.email,
			ts.phone_number,
			ts.zipcode,
			ts.cost_per_session,
			ts.style_id,
			ts.qualification_id,
            
            -- Round the sum of the matches and if it's null, consider it 0
			ifnull(round(ts.gender_pref_match + ts.style_match + ts.qualification_match + 
            ts.cost_match + ts.malady_match + ts.insurance_match + ifnull((avg(urt.rating)), 0), 2), 0) as 'match_score'
            
        from therapist_scores ts left join user_rates_therapist urt on (ts.therapist_id = urt.therapist_id)
        left join similar_users using (user_id)
        group by ts.therapist_id
		having match_score > 0
		order by match_score DESC
        limit 5;
    
    drop temporary table if exists similar_users;
    drop temporary table if exists therapist_scores;
    
end //
delimiter ;

-- Procedure to delete user from the database				
				
drop procedure if exists delete_user;

delimiter //
create procedure delete_user
(
	in username_param varchar(255)
)
begin
	declare user_id_var int;

	select user_id into user_id_var from user where username = username_param;

	delete
	from user_exhibits_malady
	where user_id = user_id_var;
	 
	delete
	from user_makes_choices
	where user_id = user_id_var;
	  
	delete
	from  user_matches_therapist
	where user_id = user_id_var;
	   
	delete
	from user_rates_therapist
	where user_id = user_id_var;

	DELETE
	FROM user
	where username = username_param;

end //
delimiter ;


-- Procedure to get a user's zipcode from their username
drop procedure if exists get_user_zipcode;

delimiter //
create procedure get_user_zipcode
(
	in username_param VARCHAR(50)
)
begin
    SELECT zipcode 
    FROM user 
    WHERE username = username_param;

end //
delimiter ;


-- Procedure to get a user's zipcode from their username
drop procedure if exists get_user_email;

delimiter //
create procedure get_user_email
(
	in username_param VARCHAR(50)
)
begin

    SELECT email 
    FROM user 
    WHERE username = username_param;
        
end //
delimiter ;

drop procedure if exists get_user_id;

delimiter //
create procedure get_user_id
(
	in username_param VARCHAR(50)
)
begin
    
    SELECT user_id 
    FROM user 
    WHERE username = username_param;
        
end //
delimiter ;

drop procedure if exists get_user_max_distance;

delimiter //
create procedure get_user_max_distance
(
	in username_param VARCHAR(50)
)
begin
    
    SELECT max_distance 
    FROM user 
    WHERE username = username_param;
        
end //
delimiter ;


-- Procedure to get therapist ID
drop procedure if exists get_therapist_id;

delimiter //
create procedure get_therapist_id
(
	in first_name_param VARCHAR(50),
    in last_name_param VARCHAR(50)
)
begin
	DECLARE therapist_id_var INT;
    
    SELECT therapist_id INTO therapist_id_var 
    FROM therapist 
    WHERE first_name = first_name_param AND last_name = last_name_param;
    
    SELECT therapist_id_var;
        
end //
delimiter ;


-- Procedure to validate password for existing user
drop procedure if exists validate_user;

delimiter //
create procedure validate_user
(
	in username_param VARCHAR(50),
    in password_param VARCHAR(20)
)
begin

	DECLARE password_actual_var VARCHAR(20);
    DECLARE result TINYINT;
    
    SELECT pwd 
    INTO password_actual_var 
    FROM user 
    WHERE username = username_param;
    
    IF (password_param = password_actual_var)
    THEN SET result = 1;
    ELSE SET result = 0;
    END IF;
    
    SELECT result;
	
end //
delimiter ;

-- Inserts a single row into the matches table
drop procedure if exists insert_matches;

delimiter //
create procedure insert_matches
(
	in user_id_param INT,
    in therapist_id_param INT,
    in score_param int
)
begin
    
	INSERT INTO user_matches_therapist
	VALUES (user_id_param, therapist_id_param, score_param);
        
end //
delimiter ;

-- ------------------ TESTS ------------------

-- test user ratings for 'kthorpc@mac.com''s similar users
insert into user_rates_therapist values
	(12, 1001, 5),
    (25, 1001, 5),
    (34, 1001, 5),
    (69, 1001, 5),
    (75, 1001, 5),
    (115, 1203, 3),
    (141, 1203, 3),
    (144, 1203, 3),
    (12, 1203, 1),
    (25, 1203, 1);

select * from user_rates_therapist;
select * from user;

call findMatchingTherapists('gwickman8@abc.net.au');

call findMatchingTherapists('kthorpc@mac.com');

call get_user_email('juni', @val);

call validate_user('juni', 'bla');

call get_therapist_id('Shelton', 'Halifax');
