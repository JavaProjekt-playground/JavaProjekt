CREATE OR REPLACE FUNCTION update_reviews()
RETURNS TRIGGER AS
$$
DECLARE

BEGIN
	UPDATE reviews
	SET updated_at =  NOW()
	WHERE id = old.rewievs_id;
	
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trig_update_reviews
AFTER INSERT OR UPDATE
ON playfields FOR EACH STATEMENT
EXECUTE PROCEDURE update_reviews();