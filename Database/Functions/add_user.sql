CREATE FUNCTION add_user(
    name VARCHAR(100),
    surnmae

)
RETURN SETOF users AS
$$
DECLARE
    res users%ROWTYPE;
BEGIN

END;
$$ LANGUAGE 'plpgsql';