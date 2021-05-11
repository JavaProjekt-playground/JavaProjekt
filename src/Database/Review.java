package Database;

import java.sql.*;

public class Review implements IUpdatableTable, IInsertableTable {
    private int id;
    public String message;
    public int score;
    public int getId(){return id;}
    public void setId(int i){id = i;}
    private int userID;
    public int getUserID() {return userID;}
    private  int playfieldID;
    public int getPlayfieldID() {return playfieldID;}
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Review(String message, int score, int plafieldid, int userid) {
        this.message = message;
        this.score = score;
        playfieldID = plafieldid;
        userID = userid;

    }
    public Review(ResultSet rs) throws SQLException {
        getDataFromResultSet(rs);
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String command = String.format("SELECT * FROM add_review('%s', %d, %d %d);",
                message, score, playfieldID, userID
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

        if (id < 1) return false;

        String command = String.format("SELECT * FROM update_review( %d, '%s', %d);",
                id, message, score
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
        id = rs.getInt("id");
        message = rs.getString("message");
        score = rs.getInt("score");
        playfieldID = rs.getInt("playfield_id");
        createdAt = rs.getTimestamp("created_at");
        updatedAt = rs.getTimestamp("updated_at");
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
