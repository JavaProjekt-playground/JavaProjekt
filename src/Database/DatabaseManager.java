package Database;

import java.sql.*;
import java.util.Dictionary;
import java.util.Properties;
import java.util.Vector;

public class DatabaseManager {

    final String _server = "ec2-54-228-9-90.eu-west-1.compute.amazonaws.com";
    final String _port = "5432";
    final String _database = "db3opa9o4csml6";
    final String _user = "dczjntmaijrisv";
    final String _password = "04d47a49ea1ae6e98887d7df0a74bd67d21a410c979ebd4e45570e018eb1d6b3";

    private Connection conn;


    public DatabaseManager(){
        String url = String.format("jdbc:postgresql://%s:%s/%s", _server, _port, _database);
        Properties props = new Properties();
        props.setProperty("user", _user);
        props.setProperty("password", _password);
        props.setProperty("ssl", "false");

        try {
            conn = DriverManager.getConnection(url, props);
        }
        catch(SQLException ex){
            System.out.println("Neki je narobe");
        }
    }

    public void TestFunction(){

        Statement st;
        ResultSet set;

        try{
            st = conn.createStatement();
            set = st.executeQuery("SELECT * FROM users;");

            while(set.next()){
                System.out.println(set.getInt(1));
            }

//            System.out.println(set.getArray(0).toString());


        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }




    }

    ///
    /// User related functions
    ///

    /**
     * Adds user to database and updates user object.
     * @param user User to add to database
     * @param password User's password
     * @return boolean True on success, False on failure
     * @throws SQLException thrown when SQL transaction fails.
     */
    public boolean addUser(User user, String password) throws SQLException {
        return user.selfInsert(conn, password);
    }

    /**
     * Updates user's database entry and refreshes user object.
     * @param user User to update.
     * @param passChk User's current password for authentication purposses
     * @param newPass User's new password (put "" if it stays the same)
     * @return boolean True on success, False on failure
     * @throws SQLException thrown when SQL transaction fails.
     */
    public boolean updateUser(User user, String passChk, String newPass) throws SQLException {
        return user.selfUpdate(conn, passChk, newPass);
    }

    /**
     * User login function
     * @param email User's email
     * @param password user's password
     * @return A User object if login is successful, else null
     * @throws SQLException thrown when SQL transaction fails.
     */
    public User userLogin(String email, String password) throws SQLException {
        User res = null;

        String sql = String.format("SELECT * FROM user_login('%s', '%s')", email, password);

        ResultSet rs = conn.createStatement().executeQuery(sql);

        if(rs.next()) res = new User(rs);

        return res;
    }

    /**
     * Deletes user from database.
     * @param user User to delete.
     * @param password User's password.
     * @return True if deletion is successful, otherwise false.
     * @throws SQLException SQL execution failure.
     */
    public boolean deleteUser(User user, String password) throws SQLException {
        String sql = String.format("SELECT * FROM delete_user(%d, '%s');", user.getID(), password);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }

    //
    // Playfield related functions
    //

    /**
     * Inserts a new playfield into the database.
     * @param playfield Playfield to insert.
     * @param pics Pictures to insert. Dictionary key is picture caption, value is path to file.
     * @param thumbnail Thumbnail picture caption.
     * @return True on success, False on failure.
     * @throws SQLException SQL execution failure.
     */
    public boolean addPlayfield(Playfield playfield, Dictionary<String, String> pics, String thumbnail) throws SQLException {
        if(playfield.selfInsert(conn)){
            while(pics.keys().hasMoreElements()){
                String k = pics.keys().nextElement();
                Picture p = new Picture(k, playfield.getID());
                if(p.selfInsert(conn, pics.get(k))){

                    if(p.Caption == thumbnail){
                        playfield.ThumbnailID = p.getId();
                        if(playfield.selfUpdate(conn)) playfield.Thumbnail = new Picture(p);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Updates a playfield database entry.
     * @param playfield Playfield to update.
     * @return True on success, False on failure.
     * @throws SQLException SQL execution failure.
     */
    public boolean updatePlayfield(Playfield playfield) throws SQLException{
        return playfield.selfUpdate(conn);
    }

    public Playfield getPlayfield(int id) throws SQLException {
        Playfield res = null;
        String sql = String.format("SELECT * FROM playfields WHERE id = %d;", id);

        Statement stmnt = conn.createStatement();

        ResultSet rs1 = stmnt.executeQuery(sql);
        if(rs1.next()){
            res = new Playfield(rs1);

            // get thumbnail
            sql = String.format("SELECT * FROM images WHERE id = %d;", res.ThumbnailID);
            ResultSet rs2 = stmnt.executeQuery(sql);
            if(rs2.next()){
                res.Thumbnail = new Picture(rs2);
            }
        }

        return res;
    }

    public Vector<Playfield> getPlayfields() throws SQLException {
        return getPlayfields(-1);
    }

    public Vector<Playfield> getPlayfields(int limit) throws SQLException {
        Vector<Playfield> res = new Vector<Playfield>();


        String sql = "SELECT * FROM playfields ORDER BY id DESC";
        if(limit > 0) sql += " LIMIT " + limit;
        sql += ";";

        ResultSet rs = conn.createStatement().executeQuery(sql);

        while(rs.next()) res.add(new Playfield(rs));

        return res;
    }

    public Vector<Regions> getRegions() throws  SQLException{
        Vector<Regions> res = new Vector<Regions>(100);
        String sql = String.format("SELECT * FROM regions;");

        Statement stmnt = conn.createStatement();

        ResultSet rs = stmnt.executeQuery(sql);

        while(rs.next()) res.add(new Regions(rs));

        return res;
    }

    public Vector<Playfield_type> getPlayfield_types() throws SQLException{
        Vector<Playfield_type> res = new Vector<>(100);
        String sql = String.format("SELECT * FROM playfield_types;");

        Statement stmnt = conn.createStatement();
        ResultSet rs = stmnt.executeQuery(sql);
        while(rs.next()) res.add(new Playfield_type(rs));
        return  res;
    }
}
