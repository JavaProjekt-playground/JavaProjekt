CREATE OR REPLACE FUNCTION update_user_updated_at()
RETURNS TRIGGER AS
$$
BEGIN
	IF OLD.updated_at = CURRENT_TIMESTAMP THEN RETURN NULL; END IF;
	UPDATE users SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
		
	RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trig_update_user_updated_at
AFTER UPDATE ON users FOR EACH ROW
EXECUTE PROCEDURE update_user_updated_at();

