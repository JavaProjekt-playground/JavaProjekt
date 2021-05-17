CREATE OR REPLACE FUNCTION insert_playfield()
RETURNS TRIGGER AS
$$
DECLARE

BEGIN
	UPDATE playfield
	SET created_at =  NOW()
	WHERE id = new.playfield_id;
	
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trig_insert_playfield
AFTER INSERT
ON playfields FOR EACH STATEMENT
EXECUTE PROCEDURE insert_playfield();