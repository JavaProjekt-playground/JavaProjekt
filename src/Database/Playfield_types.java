package Database;


import com.sun.istack.internal.NotNull;

import java.sql.*;

public class Playfield_types implements IUpdatableTable, IInsertableTable{
        private int ID;
        public int getID(){return ID;}

        public String Name;
        public String Description;


        public Playfield_types(String name, String description){
            ID = 0;
            Name = name;
            Description = description;
        }

        public Playfield_types(@NotNull ResultSet rs) throws SQLException {
            Name = rs.getString("name");
            Description = rs.getString("description");
        }

}
