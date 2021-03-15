package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    }
}
