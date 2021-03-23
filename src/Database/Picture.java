package Database;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Picture implements IUpdatableTable, IInsertableTable{
    private int id;
    public int getId(){return id;}

    public String Caption;
    public URL Url;
    private int playfieldID;
    public int getPlayfieldID(){return playfieldID;}

    public BufferedImage Image;

    public Picture(String caption, int playfieldId, BufferedImage image){
        Caption = caption;
        playfieldID = playfieldId;
        Image = image;
    }

    public Picture(ResultSet rs) throws SQLException {
        getValues(rs);
    }

    private boolean getValues(ResultSet rs) throws SQLException{
        Caption = rs.getString("caption");
        try {
            Url = new URL(rs.getString("url"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        playfieldID = rs.getInt("playfield_id");

        // TODO: image download

        return true;
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM add_image(%d, '%s');", playfieldID, Caption);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            return getValues(rs);
        }

        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM update_image(%d);", id);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            return getValues(rs);
        }

        return false;
    }
}
