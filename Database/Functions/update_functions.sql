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

CREATE OR REPLACE FUNCTION update_archive_reservations (
	order_dateR TIMESTAMP,
	from_dateR TIMESTAMP,
	to_dateR TIMESTAMP,
	paidR BOOLEAN,
	playfield_idR INTEGER,
	user_idR INTEGER
)
RETURNS SETOF archive_reservations AS
$$
DECLARE
	res archive_reservations%ROWTYPE;
BEGIN
	UPDATE archive_reservations
	SET order_date = order_dateR,
	 	from_date = from_dateR,
		to_date = to_dateR,
		paid = paidR,
		playfield_id = playfield_idR,
		user_id = user_idR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION update_playfields (
	titleR VARCHAR(255),
	descriptionR TEXT,
	phoneR VARCHAR(20),
	emailR VARCHAR(255),
	websiteR VARCHAR(255),
	addressR VARCHAR(255),
	region_idR INTEGER,
	owner_idR TIMESTAMP,
	title_image_idR INTEGER,
	type_idR INTEGER,
	price_per_hourR REAL
)
RETURNS SETOF playfields AS
$$
DECLARE
	res playfields%ROWTYPE;
BEGIN
	UPDATE playfields SET
		title = titleR, 
		description = descriptionR, 
		phone = phoneR, 
		email = emailR,
		website = websiteR, 
		address = adressR, 
		region_id = region_idR, 
		owner_id = owner_idR, 
		title_image_id = title_image_idR, 
		type_id = type_idR, 
		price_per_hour = price_per_hourR
	RETURNING * INTO res;
	RETURN NEXT res;
END
$$LANGUAGE 'plpgsql';