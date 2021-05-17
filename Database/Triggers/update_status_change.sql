CREATE OR REPLACE FUNCTION update_status_change()
RETURNS TRIGGER AS
$$
DECLARE

BEGIN
	IF old.reservations_status_id != new.reservations_status_id
	THEN
	UPDATE reservations
	SET status_change_date = NOW()
	WHERE id = old.reservations_id;
	END IF;
	
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER triger_update_status_change
AFTER UPDATE
ON playfields FOR EACH STATEMENT
EXECUTE PROCEDURE update_status_change();