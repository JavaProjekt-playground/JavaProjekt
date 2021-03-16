import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JavaProject {

    public static void main(String[] args){
        DatabaseManager db = new DatabaseManager();

        db.TestFunction();
    }
}
