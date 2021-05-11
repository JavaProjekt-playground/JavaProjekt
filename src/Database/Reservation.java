package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Reservation implements IUpdatableTable, IInsertableTable {
    private int id;
    public int getID(){return id;}

    public Timestamp FromDate;
    public Timestamp ToDate;
    public Timestamp OrderDate;
    public boolean IsPaid;
    public int StatusID;
    public int PlayfieldID;
    private int userID;
    public int getUserID(){return userID;}

    public Reservation(Timestamp from, Timestamp to, Timestamp date, boolean isPaid, int statusid, int userid, int playfieldid){
        FromDate = from;
        ToDate = to;
        OrderDate = date;
        IsPaid = isPaid;
        StatusID = statusid;
        PlayfieldID = playfieldid;
        userID = userid;
    }

    public Reservation(ResultSet rs) throws SQLException {
        getValues(rs);
    }

    private void getValues(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        FromDate = rs.getTimestamp("from_date");
        ToDate = rs.getTimestamp("to_date");
        OrderDate = rs.getTimestamp("order_date");
        IsPaid = rs.getBoolean("paid");
        PlayfieldID = rs.getInt("playfield_id");
        userID = rs.getInt("user_id");
        StatusID = rs.getInt("status_id");
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM add_reservation('%s', '%s', %d, %d, %s);",
                FromDate.toString(),
                ToDate.toString(),
                userID,
                PlayfieldID,
                IsPaid ? "TRUE" : "FALSE");

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);
            return true;
        }
        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM update_reservation('%s', '%s', %d, %d, %s);",
                FromDate.toString(),
                ToDate.toString(),
                userID,
                PlayfieldID,
                IsPaid ? "TRUE" : "FALSE");

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);
            return true;
        }
        return false;
    }
}
