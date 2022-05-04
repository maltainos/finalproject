INSERT INTO users(created_on, email, encrypted_password, first_name, last_name, user_id) VALUES (now(), "rony@rvw.com", "$2a$10$IBCcZDGZ.u3hseRASVH41OTlAQsTd5xa0LEVdkfLq4U/6m2rPikEO", "Rony", "Zaona","2yEQpuy6r7NtVaVii1kNqSKTgi0pml");
	
INSERT INTO levels 
	(level_id, level_name, created_on) 
	VALUES ("2yEQpuy6r7NtVaVii1kNqSKTgi0pml", "Administrator",now());
	
INSERT INTO users_levels 
	(levels_id,users_id) 
	VALUES (1,1);
	
