CREATE OR REPLACE FUNCTION delete_user(
	a_id INT,
	a_pass VARCHAR(255)
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM users
	WHERE id = a_id
	AND password = SHA1(a_pass)
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one user. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';