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
    public String getFileName(){return fileName;}
    private int playfieldID;
    public int getPlayfieldID(){return playfieldID;}
    public void setPlayfieldID(int i){
        if(i > 0) playfieldID = i;
    }
    private String filePath;
    public String getFilePath(){return filePath;}

    public URL getURL(){
        if(id > 0) return ImageLoader.getImageURL(fileName);
        return null;
    }

    public Picture(String caption, String path){
        if(caption == null || path == null) throw new NullPointerException("Parameters cannot be null");
        Caption = caption;
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
        id = rs.getInt("id");
        Caption = rs.getString("caption");
        fileName = rs.getString("url");
        playfieldID = rs.getInt("playfield_id");
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        if(extra[0] == null){
            throw new NullPointerException("First extra argument must be boolean.");
        }

        boolean isThumb = (boolean)extra[0];

        String suffix = filePath.toLowerCase().substring(filePath.lastIndexOf(".") + 1);

        String sql = String.format("SELECT * FROM add_image(%d, '%s', '%s', %b);", playfieldID, Caption, suffix, isThumb);

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
