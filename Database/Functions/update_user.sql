CREATE OR REPLACE FUNCTION update_user(
	a_id INT,
	a_name VARCHAR(100),
	a_surname VARCHAR(100),
	a_email VARCHAR(100),
	a_bdate TIMESTAMP,
	a_passchk VARCHAR(255),
	a_phone VARCHAR(20) DEFAULT NULL,
	a_password VARCHAR(255) DEFAULT NULL
)
RETURNS SETOF users AS
$$
DECLARE
	res users%ROWTYPE;
	newpass VARCHAR(255);
BEGIN

	IF a_password IS NOT NULL THEN 
		newpass := MD5(a_password);
	ELSE
		SELECT INTO newpass password FROM users WHERE id = a_id AND password = MD5(a_passchk);
	END IF;
	
	UPDATE users
	SET name = a_name,
	surname = a_surname,
	email = a_email,
	bdate = a_bdate,
	phone = a_phone,
	password = newpass
	WHERE id = a_id
	AND password = MD5(a_passchk)
	RETURNING * INTO res;
	
	IF res.id IS NOT NULL THEN RETURN NEXT res; 
	ELSE 
		RETURN QUERY SELECT * FROM USERS WHERE id = -1;
	END IF; 
	
END;
$$ LANGUAGE 'plpgsql';