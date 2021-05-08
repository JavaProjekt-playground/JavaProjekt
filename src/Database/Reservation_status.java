package Database;

import com.sun.istack.internal.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation_status implements IUpdatableTable, IInsertableTable
{
    private int ID;
    public int getID(){return ID;}

    public String Title;
    public String Description;


    public Reservation_status(String title, String description){
        ID = 0;
        Title = title;
        Description = description;
    }

    public Reservation_status(@NotNull ResultSet rs) throws SQLException {
        ID = rs.getInt("id");
        Title = rs.getString("title");
        Description = rs.getString("description");
    }



}

