CREATE TYPE info_bundle AS
(
	users_count INT,
	playfields_count INT,
	images_count INT,
	max_score REAL,
	min_score REAL,
	avg_score REAL
);

CREATE OR REPLACE FUNCTION get_info_bundle()
RETURNS info_bundle AS
$$
DECLARE
	res info_bundle;
BEGIN

	SELECT INTO res.users_count COUNT(*) FROM users;
	
	SELECT INTO res.playfields_count, res.max_score, res.min_score, res.avg_score
	COUNT(*), MAX(avg_score), MIN(avg_score), AVG(avg_score) FROM playfields;
	
	SELECT INTO res.images_count COUNT(*) FROM images;

	RETURN res;
END;
$$ LANGUAGE 'plpgsql';