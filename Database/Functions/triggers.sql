CREATE OR REPLACE FUNCTION insert_reservation_trigger()
RETURNS TRIGGER AS $$
BEGIN
	UPDATE reservations SET order_date = now(), status_id = 1 WHERE id = new.id;
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER insert_reservation AFTER INSERT on reservations
FOR EACH ROW EXECUTE PROCEDURE insert_reservation_trigger();
