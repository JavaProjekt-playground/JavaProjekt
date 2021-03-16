CREATE OR REPLACE FUNCTION add_region (nameR VARCHAR (100), postcodeR VARCHAR (5))
RETURNS SETOF regions AS
$$
DECLARE
	res regions%ROWTYPE;
BEGIN
	INSERT INTO regions (name, postcode) VALUES (nameR, postcodeR)
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
	INSERT INTO reservation_statuses (title, description) VALUES (titleR, descriptionR)
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION add_review (
	messageR TEXT,
	scoreR REVIEW_SCORE,
	playfield_idR INTEGER,
	user_idR INTEGER
)
RETURNS SETOF reviews AS
$$
DECLARE
	res reviews%ROWTYPE;
BEGIN
	INSERT INTO reviews (message, score, playfield_id, user_id) VALUES (messageR, scoreR, playfield_idR, user_idR)
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION add_reservation (
	from_dateR TIMESTAMP,
	to_dateR TIMESTAMP,
	user_idR INTEGER,
	playfield_idR INTEGER,
	paid BOOL
)
RETURNS SETOF reservations AS
$$
DECLARE
	res reservations%ROWTYPE;
BEGIN
	INSERT INTO reservations (from_date, to_date, user_id, playfield_id, paid) VALUES (from_dateR, to_dateR, user_idR, playfield_idR, paidR)
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION add_playfield_type (nameR VARCHAR (100), descriptionR TEXT)
RETURNS SETOF playfield_types AS
$$
DECLARE
	res playfield_types%ROWTYPE;
BEGIN
	INSERT INTO playfield_types (name, description) VALUES (nameR, descriptionR)
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION add_image (urlI VARCHAR (100), captionI TEXT, playfield_idI INTEGER)
RETURNS SETOF images AS
$$
DECLARE
	res images%ROWTYPE;
BEGIN
	INSERT INTO images (url, caption, playfield_id) VALUES (urlI, captionI, playfield_idI)
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';