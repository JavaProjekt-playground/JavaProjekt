CREATE OR REPLACE FUNCTION add_region (nameR VARCHAR (100), postcodeR VARCHAR (5))
RETURNS SETOF regions AS
$$
DECLARE
	res regions%ROWTYPE;
BEGIN
	INSERT INTO regions (name, postcode) VALUES (nameR, postcodeR);
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION add_reservation_status (titleR VARCHAR(100), descriptionR TEXT)
RETURNS SETOF reservation_statuses AS
$$
DECLARE
	res reservation_statuses%ROWTYPE;
BEGIN
	INSERT INTO reservation_statuses (title, description) VALUES (titleR, descriptionR);
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

