CREATE TABLE addresses(
	id BIGINT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
	address_Id VARCHAR(30) NOT NULL UNIQUE,
	city VARCHAR(30) NOT NULL,
	street VARCHAR(75) NOT NULL,
	avenida VARCHAR(75),
	cell VARCHAR(15),
	users_id BIGINT NOT NULL,
	FOREIGN KEY(users_id) REFERENCES users(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;