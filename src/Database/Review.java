package Database;

import java.sql.*;

public class Review implements IUpdatableTable, IInsertableTable {
    private int ID;
    public String Message;
    public Double Score;
    public int getID(){return ID;}
    private int userID;
    public int getUserID() {return userID;}
    private  int playfieldID;
    public int getPlayfieldID() {return playfieldID;}
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Review(String message, double score, int plafieldID, int UserID, Timestamp cretedAt, Timestamp updateAt) {
        ID = getID();
        Message = message;
        Score = score;
        playfieldID = getPlayfieldID();
        userID = getUserID();
        Timestamp Created_At = cretedAt;
        Timestamp Updated_At = updateAt;
    }
    public Review(ResultSet rs) throws SQLException {
        getDataFromResultSet(rs);
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String command = String.format("SELECT * FROM add_review('%s', '%s', '%s', '%s', '%s', '%s');",
                Message, Score, playfieldID, userID, timestamp.toString(), timestamp.toString()
        );

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(command);

        if(rs.next()){
            getDataFromResultSet(rs);
            return true;
        }

        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {

        if (ID < 1) return false;

        String command = String.format("SELECT * FROM update_review( %d, '%s', '%s', '%s', '%s', '%s', '%s');",
                ID, Message, Score, playfieldID, userID, timestamp.toString(), timestamp.toString()
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
        Message = rs.getString("textArea1");
        Score = rs.getDouble("Rating");
    }
}
