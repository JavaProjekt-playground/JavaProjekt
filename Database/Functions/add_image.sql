CREATE FUNCTION add_image(a_playfield_id INT, a_caption TEXT)
RETURNS SETOF images AS
$$
DECLARE
    res images%ROWTYPE;
    folder TEXT;
BEGIN
    folder := 'tempfolder/';
    INSERT INTO images (caption, url, playfield_id)
    VALUES (a_caption, 'tempfolder/image'||CURRVAL('images_id_seq')||'.jpg' ,a_playfield_id)
    RETURNING * INTO res;
    
    RETURN NEXT res;
END;
$$ LANGUAGE 'plpgsql';