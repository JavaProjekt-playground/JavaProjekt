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
    private String fileName;
    private int playfieldID;
    public int getPlayfieldID(){return playfieldID;}

    private String filePath;

    public URL getURL(){
        return ImageLoader.getImageURL(fileName);
    }

    public Picture(String caption, int playfieldId, String path){
        Caption = caption;
        playfieldID = playfieldId;
        filePath = path;
    }

    public Picture(ResultSet rs) throws SQLException {
        getValues(rs);
    }

    public Picture(Picture p){
        id = p.id;
        fileName = p.fileName;
        playfieldID = p.getPlayfieldID();
    }

    private void getValues(ResultSet rs) throws SQLException{
        Caption = rs.getString("caption");
        fileName = rs.getString("url");
        playfieldID = rs.getInt("playfield_id");
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM add_image(%d, '%s');", playfieldID, Caption);
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);

            if(ImageLoader.FTPLoader.uploadImage(filePath, fileName)) return true;

            sql = String.format("SELECT * FROM delete_images(%d);", id);
            conn.createStatement().executeQuery(sql);
        }

        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM update_image(%d);", id);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);
            return true;
        }

        return false;
    }
}
