package Database;

import java.sql.*;

/**
 * import org.jetbrains.annotations.NotNull;
 */

public class User implements IUpdatableTable, IInsertableTable
{
    private int ID;

    public int getID(){return ID;}

    public String Name;
    public String Surname;
    public String Email;
    public String Phone;
    public Timestamp BDate;

    private Timestamp _createdAt;
    public Timestamp getCreatedAt() {return _createdAt;}

    private Timestamp _updatedAt;
    public Timestamp getUpdatedAt() {return _updatedAt;}

    public User(String name, String surname, String email, String phone, Timestamp bDate){
        ID = 0;
        Name = name;
        Surname = surname;
        Email = email;
        Phone = phone;
        BDate = bDate;
    }

    public User(ResultSet rs) throws SQLException {
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
        String command = String.format("SELECT * FROM add_user('%s', '%s', '%s', '%s', %s, '%s');",
            Name, Surname, BDate.toString(), Email,
            Phone != "" ? "'" + Phone + "'" : "NULL",
            extra[0]
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
    @Override
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
    }

    private void getDataFromResultSet(ResultSet rs) throws SQLException {
        ID = rs.getInt("id");
        Name = rs.getString("name");
        Surname = rs.getString("surname");
        Email = rs.getString("email");
        Object p = rs.getString("phone");
        Phone = p != null && (String)p != "" ? (String)p : "";
        BDate = rs.getTimestamp("bdate");
    }

}
