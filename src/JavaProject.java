import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import Database.*;

public class JavaProject {

    public static void main(String[] args){
        DatabaseManager db = new DatabaseManager();

        db.TestFunction();
        try {
            User u = new User("Test", "Test", "mail@me.com", "123456789", new Timestamp(0));
            db.addUser(u, "password");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
