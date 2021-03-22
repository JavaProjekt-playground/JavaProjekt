CREATE OR REPLACE FUNCTION update_region (
	nameR VARCHAR (100),
	postcodeR VARCHAR (5)
)
RETURNS SETOF regions AS
$$
DECLARE
	res regions%ROWTYPE;
BEGIN
	UPDATE regions SET
		name = nameR,
	 	postcode = postcodeR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION update_review (
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
	UPDATE reviews
	SET message = messageR,
	 	score = scoreR,
		playfield_id = playfield_idR,
		user_id = user_idR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION update_reservation (
	from_dateR TIMESTAMP,
	to_dateR TIMESTAMP,
	user_idR INTEGER,
	playfield_idR INTEGER,
	paidR BOOL
)
RETURNS SETOF reservations AS
$$
DECLARE
	res reservations%ROWTYPE;
BEGIN
	UPDATE reservations
	SET from_date = from_dateR,
	 	to_date = to_dateR,
		playfield_id = playfield_idR,
		user_id = user_idR,
		paid = paidR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';




CREATE OR REPLACE FUNCTION update_playfield_type (
	nameR VARCHAR (100),
	descriptionR TEXT
)
RETURNS SETOF playfield_types AS
$$
DECLARE
	res playfield_types%ROWTYPE;
BEGIN
	UPDATE playfield_types
	SET name = nameR,
	 	description = descriptionR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION update_reservation_status (
	titleR VARCHAR (100),
	descriptionR TEXT
)
RETURNS SETOF reservation_statuses AS
$$
DECLARE
	res reservation_statuses%ROWTYPE;
BEGIN
	UPDATE reservation_statuses
	SET title = titleR,
	 	description = descriptionR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';



CREATE OR REPLACE FUNCTION update_image (
	urlI VARCHAR (100), 
	captionI TEXT, 
	playfield_idI INTEGER
)
RETURNS SETOF images AS
$$
DECLARE
	res images%ROWTYPE;
BEGIN
	UPDATE images
	SET url = urlI,
	 	caption = captionI,
		playfield_id = playfield_idI
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';