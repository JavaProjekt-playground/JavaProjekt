CREATE OR REPLACE FUNCTION check_date_reservation(from_d timestamp, to_d timestamp, p_id INTEGER)
RETURNS INTEGER AS $$
DECLARE
	check INTEGER;
BEGIN
	SELECT INTO check count(*) FROM reservations WHERE ((from_d BETWEEN from_date AND to_date) OR
	(to_d BETWEEN from_date AND to_date)) AND (playfield_id = p_id);
	IF from_d > to_d THEN
	check := 1;
	END IF;
	RETURN check;
END;
$$ LANGUAGE 'plpgsql';

SELECT * FROM check_date_reservation('2021-05-08', '2021-05-11',33);