CREATE OR REPLACE FUNCTION user_login
(
	a_email VARCHAR(255),
	a_password VARCHAR(255)
)
RETURNS SETOF users AS
$$
DECLARE
	res users%ROWTYPE;
BEGIN
	SELECT INTO res * FROM users WHERE email = a_email AND password = SHA1(a_password) LIMIT 1;
	res.password = NULL;
	RETURN NEXT res;
END;
$$ LANGUAGE 'plpgsql';