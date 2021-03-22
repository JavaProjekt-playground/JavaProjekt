package Database;

import com.sun.istack.internal.NotNull;
import sun.security.krb5.internal.crypto.Des;

import java.sql.*;

public class Reservation_statuses
{
    private int ID;
    public int getID(){return ID;}

    public String Title;
    public String Description;


    public Reservation_statuses (String title, String description){
        ID = 0;
        Title = title;
        Description = description;
    }

    public Reservation_statuses(@NotNull ResultSet rs) throws SQLException {
        Title = rs.getString("title");
        Description = rs.getString("description");
    }

}

