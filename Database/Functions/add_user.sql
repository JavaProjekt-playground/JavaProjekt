CREATE OR REPLACE FUNCTION add_user(
    a_name VARCHAR(100),
    a_surname VARCHAR(100),
    a_bdate TIMESTAMP,
    a_email VARCHAR(100),
    a_phone VARCHAR(20),
    a_password VARCHAR(255)
)
RETURNS SETOF users AS
$$
DECLARE
    res users%ROWTYPE;
BEGIN
    INSERT INTO users (name, surname, bdate, email, phone, password, created_at, updated_at)
    VALUES (a_name, a_surname, a_bdate, a_email, a_phone, SHA1(a_password), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    RETURNING * INTO res;

    RETURN NEXT res;
END;
$$ LANGUAGE 'plpgsql';