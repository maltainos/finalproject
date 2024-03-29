CREATE TABLE users (
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(30) NOT NULL, 
	first_name VARCHAR(75) NOT NULL, 
	last_name VARCHAR(25) NOT NULL,
	email VARCHAR(120) NOT NULL UNIQUE,
	email_verification_status BOOLEAN DEFAULT FALSE,
	email_verification_token VARCHAR(255), 
	encrypted_password VARCHAR(120) NOT NULL, 
	created_on DATE NOT NULL, 
	updated_on DATETIME,
	primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;