package Database;


import com.sun.istack.internal.NotNull;

import java.sql.*;

public class Playfield_type implements IUpdatableTable, IInsertableTable{
        private int ID;
        public int getID(){return ID;}

        public String Name;
        public String Description;


        public Playfield_type(String name, String description){
            ID = 0;
            Name = name;
            Description = description;
        }

        public Playfield_type(@NotNull ResultSet rs) throws SQLException {
            Name = rs.getString("name");
            Description = rs.getString("description");
        }

    @Override
    public String toString() {
        return Name;
    }
}
