package Database;

import com.sun.istack.internal.NotNull;

import java.sql.*;

public class Regions
{
    private int ID;
    public int getID(){return ID;}

    public String Name;
    public String Postcode;

    public Regions(String name, String postcode){
        ID = 0;
        Name = name;
        Postcode = postcode;
    }

    public Regions(@NotNull ResultSet rs) throws SQLException {
        ID = rs.getInt("id");
        Name = rs.getString("name");
        Postcode = rs.getString("postcode");
    }

    @Override
    public String toString() {
        return Name;
    }
}
