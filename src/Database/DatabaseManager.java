package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseManager {

    private Connection conn;


    public DatabaseManager(){
        String url = "jdbc:postgresql://ec2-54-228-9-90.eu-west-1.compute.amazonaws.com:5432/db3opa9o4csml6";
        Properties props = new Properties();
        props.setProperty("user", "dczjntmaijrisv");
        props.setProperty("password", "04d47a49ea1ae6e98887d7df0a74bd67d21a410c979ebd4e45570e018eb1d6b3");
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
     * @return boolean True on success, False on faliure
     * @throws SQLException
     */
    public boolean addUser(User user, String password) throws SQLException {
        return user.selfInsert(conn, password);
    }

    /**
     * Updates user's database entry and refreshes user object.
     * @param user User to update.
     * @param passChk User's current password for authentication purposess
     * @param newPass User's new password (pass "" if it stays the same)
     * @return boolean True on success, False on faliure
     * @throws SQLException
     */
    public boolean updateUser(User user, String passChk, String newPass) throws SQLException {
        return user.selfUpdate(conn, passChk, newPass);
    }
}
