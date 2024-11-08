CREATE TABLE users(
	user_id UUID,
	email VARCHAR(100) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role CHAR(5) NOT NULL,
	is_account_non_expired BOOLEAN NOT NULL,
	is_account_non_locked BOOLEAN NOT NULL,
	is_credentials_non_expired BOOLEAN NOT NULL,
	is_enabled BOOLEAN NOT NULL,
	
	CONSTRAINT PK_USER PRIMARY KEY(user_id),
	CONSTRAINT U_USER_EMAIL UNIQUE(email),
	CONSTRAINT CK_ROLE CHECK (role IN('ADMIN', 'USER'))
);