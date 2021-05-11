package Database;

import FrontEnd.App;

import java.sql.*;
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

    public boolean updateUser1(int ID, User user, String passChk, String newPass) throws SQLException{
        String command = String.format("SELECT * FROM update_user(%d::INTEGER, '%s'::VARCHAR, '%s'::VARCHAR, '%s'::TIMESTAMP, '%s'::VARCHAR, '%s'::VARCHAR, %s::VARCHAR, '%s'::VARCHAR);",
                App.getCurrentUser().getID(),user.Name, user.Surname, user.BDate.toString(), user.Email, passChk,
                user.Phone != "" ? "'" + user.Phone + "'" : "NULL",  newPass
        );
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(command);

        if(rs.next()){
            //getDataFromResultSet(rs);
            return true;
        }

        return false;
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
     * @param pics Pictures to insert.
     * @param thumbnail Thumbnail picture.
     * @return True on success, False on failure.
     * @throws SQLException SQL execution failure.
     */


    public boolean addPlayfield(Playfield playfield, Vector<Picture> pictures, Picture thumbnail) throws SQLException {
        if(playfield.selfInsert(conn)){
            for (Picture pic : pictures) {
                pic.setPlayfieldID(playfield.getID());
                pic.selfInsert(conn,pic == thumbnail);
            }
        }
        return true;
    }

    public boolean addPlayfieldTest(Playfield playfield) throws SQLException {
        return playfield.selfInsert(conn);
    }

    /**
     * Updates a playfield database entry.
     * @param playfield Playfield to update.
     * @return True on success, False on failure.
     * @throws SQLException SQL execution failure.
     */
    public boolean updatePlayfield(Playfield playfield, Vector<Picture> picsToAdd, Vector<Picture> picsToRemove) throws SQLException{
        for(Picture p : picsToAdd){
            p.selfInsert(conn, p == playfield.Thumbnail);
        }

        for(Picture p : picsToRemove){
            deletePicture(p);
        }

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

        for(Playfield pf : res){
            pf.Thumbnail = getPicture(pf.ThumbnailID);
        }

        return res;
    }






    //REGIONS

    public Regions getRegion(int id) throws SQLException {
        String sql = String.format("SELECT * FROM regions WHERE id = %d;", id);

        ResultSet rs = conn.createStatement().executeQuery(sql);

        Regions res = null;
        if(rs.next()) res = new Regions(rs);

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

    public Regions getRegions(int regions_id) throws  SQLException{
        Regions res = null;
        String sql = String.format("SELECT * FROM regions WHERE id = %d ", regions_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Regions(rs);
        return res;
    }

    public boolean updateRegions(Regions regions) throws SQLException{
        return regions.selfUpdate(conn);
    }

    public boolean deleteRegions(Regions regions) throws SQLException {
        String sql = String.format("SELECT * FROM delete_regions(%d,);", regions.getID());

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }






    //PLAYFIELD TYPES
    public Vector<Playfield_type> getPlayfield_types() throws SQLException{
        Vector<Playfield_type> res = new Vector<>(100);
        String sql = String.format("SELECT * FROM playfield_types;");

        Statement stmnt = conn.createStatement();
        ResultSet rs = stmnt.executeQuery(sql);
        while(rs.next()) res.add(new Playfield_type(rs));
        return res;
    }

    public Playfield_type getPlayfield_type(int playfield_Type_id) throws  SQLException{
        Playfield_type res = null;
        String sql = String.format("SELECT * FROM playfield_types WHERE id = %d ", playfield_Type_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Playfield_type(rs);
        return res;
    }

    public boolean updatePlayfield_types(Playfield_type playfieldType) throws SQLException{
        return playfieldType.selfUpdate(conn);
    }

    public boolean deletePlayfield_type(Playfield_type playfield_type) throws SQLException {
        String sql = String.format("SELECT * FROM delete_playfield_type(%d,);", playfield_type.getID());

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }











    //RESERVATIONS

    public Vector<Reservation> getReservation(int user) throws SQLException{
        Vector<Reservation> res = new Vector<>(10);
        String sql = String.format("SELECT * FROM reservations WHERE user_id = %d", user);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res.add(new Reservation(rs));
        return res;
    }

    public Reservation getReservation_id(int reservation_id) throws  SQLException{
        Reservation res = null;
        String sql = String.format("SELECT * FROM reservations WHERE id = %d ", reservation_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Reservation(rs);
        return res;
    }

    public boolean addReservation(Reservation reservation) throws SQLException{
        return reservation.selfInsert(conn);
    }

    public boolean updateReservation(Reservation reservation) throws SQLException{
        return reservation.selfUpdate(conn);
    }

    public boolean deleteReservations(Reservation reservation) throws SQLException {
        String sql = String.format("SELECT * FROM delete_reservations(%d,);", reservation.getID());

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }













    //RESERVATION STATUSES
    public Vector<Reservation_status> getreservation_status() throws  SQLException{
        Vector<Reservation_status> res = new Vector<>(5);
        String sql = String.format("SELECT * FROM reservation_statuses");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res.add(new Reservation_status(rs));
        return res;
    }

    public Reservation_status getreservation_status_Reservation_id(int reservation_id) throws  SQLException{
        Reservation_status res = null;
        String sql = String.format("SELECT * FROM reservation_statuses rs INNER JOIN reservations r ON " +
                "rs.id = r.reservation_status_is WHERE reservation = %d ", reservation_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Reservation_status(rs);
        return res;
    }

    public boolean updateReservation_status(Reservation_status reservation_status) throws SQLException{
        return reservation_status.selfUpdate(conn);
    }

    public boolean deleteReservation_status(Reservation_status reservation_status) throws SQLException {
        String sql = String.format("SELECT * FROM delete_reservation_status(%d,);", reservation_status.getID());

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }












    //REVIEWS
    public Vector<Review> getReviews(int playfield_id) throws  SQLException{
        Vector<Review> res = new Vector<Review>(100);
        String sql = String.format("SELECT * FROM reviews r INNER JOIN playfields p ON " +
                "p.id = r.playfield_id WHERE p.id = %d ;", playfield_id);
        Statement stmnt = conn.createStatement();
        ResultSet rs = stmnt.executeQuery(sql);
        while(rs.next()) res.add(new Review(rs));
        return res;
    }

    public boolean addReview(Review review) throws SQLException {
        return review.selfInsert(conn);
    }

    public Review getReview(int review_id) throws  SQLException{
        Review res = null;
        String sql = String.format("SELECT * FROM reviews WHERE id = %d ", review_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Review(rs);
        return res;
    }

    public boolean updateReviews(Review review) throws SQLException{
        return review.selfUpdate(conn);
    }

    public boolean deleteReviews(Review review) throws SQLException {
        String sql = String.format("SELECT * FROM delete_reviews(%d,);", review.getId());
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }













    //PICTURE
    public Vector<Picture> getPictures(int playfield_id) throws  SQLException{
        Vector<Picture> res = new Vector<Picture>();
        String sql = String.format("SELECT * FROM images p INNER JOIN playfields pl ON " +
                "pl.id=p.playfield_id WHERE pl.id = %d ;", playfield_id);
        Statement stmnt = conn.createStatement();
        ResultSet rs = stmnt.executeQuery(sql);
        while(rs.next()) res.add(new Picture(rs));
        return res;
    }

    public Picture getPicture(int picture_id) throws  SQLException{
        Picture res = null;
        String sql = String.format("SELECT * FROM images WHERE id = %d ", picture_id);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) res = new Picture(rs);
        return res;
    }

    public boolean updatePicture(Picture picture) throws SQLException{
        return picture.selfUpdate(conn);
    }

    public boolean deletePicture(Picture picture) throws SQLException {
        String sql = String.format("SELECT * FROM delete_images(%d);", picture.getId());
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()) return rs.getBoolean(1);
        return false;
    }

    public User getUser(int userID) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE id = %d;", userID);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        User res = null;
        if(rs.next()) res = new User(rs);

        return res;
    }


    public int CheckDateReservation(Timestamp from, Timestamp to) throws SQLException {
        String sql = String.format("SELECT * FROM check_date_reservation('%s', '%s')", from, to);
        ResultSet rs = conn.createStatement().executeQuery(sql);
        Integer res = null;
        if(rs.next()) res = rs.getInt(1);
        return res;
    }
}
