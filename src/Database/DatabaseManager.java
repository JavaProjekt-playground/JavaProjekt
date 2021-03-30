package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
            return;
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
}
