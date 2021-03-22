package Database;

import com.sun.istack.internal.NotNull;
/**import org.jetbrains.annotations.NotNull;*/

import java.sql.*;

public class Regions implements IUpdatableTable, IInsertableTable
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
        getDataFromResultSet(rs);
    }

    /**
     * Inserts user object into database.
     * @param conn Database connection.
     * @param extra Extra required parameters:
     *              1 (required) String - password
     * @return boolean True on success, False on faliure.
     */
    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String command = String.format("SELECT * FROM add_region ('%s', '%s','%s');",
                Name, Postcode,  extra[0]
        );

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(command);

        if(rs.next()){
            getDataFromResultSet(rs);
            return true;
        }

        return false;
    }

    /**
     * Updates user's database entry
     * @param conn Database connection
     * @param extra extra required parameters:
     *              1 (required) String - current password for authentication purposess
     *              2 (optional) String - new password
     * @return boolean True on success, False on faliure.
     */
    /**@Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {

        if (ID < 1) return false;

        String command = String.format("SELECT * FROM update_user( %d, '%s', '%s', '%s', '%s', '%s', %s, '%s' );",
                ID, Name, Surname, Email, BDate.toString(), extra[0],
                Phone != "" ? "'" + Phone + "'" : "NULL",
                extra.length > 1 && extra[1] != "" ? "'" + extra[1] + "'" : "NULL"
        );

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(command);

        if(rs.next()){
            getDataFromResultSet(rs);
            return true;
        }

        return false;
    }*/

    private void getDataFromResultSet(ResultSet rs) throws SQLException {
        ID = rs.getInt("id");
        Name = rs.getString("name");
        Postcode = rs.getString("postcode");
    }
}
