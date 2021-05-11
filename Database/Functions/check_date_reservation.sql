CREATE OR REPLACE FUNCTION check_date_reservation(from_d timestamp, to_d timestamp)
RETURNS INTEGER AS $$
DECLARE
	check INTEGER;
BEGIN
	SELECT INTO check count(*) FROM reservations WHERE (from_d BETWEEN from_date AND to_date) OR
	(to_d BETWEEN from_date AND to_date);
	RETURN check;
END;
$$ LANGUAGE 'plpgsql';

SELECT * FROM check_date_reservation('2021-06-08', '2021-06-11');