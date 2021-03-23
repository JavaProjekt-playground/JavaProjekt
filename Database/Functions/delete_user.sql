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
	AND password = MD5(a_pass)
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

CREATE OR REPLACE FUNCTION delete_archive_reservations(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM archive_reservations
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one archive reservation. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION delete_images(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM images
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one image. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION delete_playfield_types(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM playfield_types
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one Playfield type. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION delete_playfields(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM playfields
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one Playfield. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION delete_regions(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM regions
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one Region. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION delete_reservation_statuses(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM reservation_statuses
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one reservation status. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION delete_reviews(
	a_id INT
)
RETURNS BOOLEAN AS
$$
DECLARE
	res INT;
BEGIN
	DELETE FROM reviews
	WHERE id = a_id
	RETURNING * INTO res;
	
	IF res = 1 THEN
		RETURN TRUE;
	ELSIF res = 0 THEN
		RETURN FALSE;
	ELSE
		RAISE EXCEPTION 'Attempted to delete more than one reservation. (Tried %)', res;
	END IF;
END;
$$ LANGUAGE 'plpgsql';