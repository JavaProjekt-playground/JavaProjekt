CREATE OR REPLACE FUNCTION update_avg_score()
RETURNS TRIGGER AS
$$
DECLARE
 	p_id INT;
BEGIN
	IF NEW IS NOT NULL THEN
		p_id := NEW.playfield_id;
	ELSE
		p_id := OLD.playfield_id;
	END IF;
	
	UPDATE playfields
	SET avg_score = (SELECT AVG(score) FROM reviews WHERE playfield_id = p_id)
	WHERE id = p_id;
	
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trig_update_avg_score
AFTER INSERT OR UPDATE OR DELETE
ON reviews FOR EACH ROW
EXECUTE PROCEDURE update_avg_score();