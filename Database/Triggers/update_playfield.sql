CREATE OR REPLACE FUNCTION update_playfield()
RETURNS TRIGGER AS
$$
DECLARE

BEGIN
	UPDATE playfield
	SET updated_at =  NOW()
	WHERE id = old.playfield_id;
	
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trig_update_playfield
AFTER INSERT OR UPDATE
ON playfields FOR EACH STATEMENT
EXECUTE PROCEDURE update_playfield();