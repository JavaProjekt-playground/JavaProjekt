package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Review implements IUpdatableTable, IInsertableTable {
    private int id;
    public int getID(){ return id; }
    private int userID;
    public int getUserID() { return userID; }
    private int playfieldID;
    public int getPlayfieldID() { return playfieldID; }
    private int score;
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score < 6 && score > 0) this.score = score;
        else throw new IllegalArgumentException("Score value must be between must be at least 1 and at most 5");
    }


    private Timestamp createdAt;
    public Timestamp getCreatedAt() { return createdAt; }
    private Timestamp updatedAt;
    public Timestamp getUpdatedAt() { return updatedAt; }

    public String Message;

    public Review(int userId, int playfieldId, String message){
        userID = userId;
        playfieldID = playfieldId;
        Message = message;
    }

    public Review(ResultSet rs) throws SQLException {
        getValues(rs);
    }

    private void getValues(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        userID = rs.getInt("user_id");
        playfieldID = rs.getInt("playfield_id");
        Message = rs.getString("message");
        createdAt = rs.getTimestamp("created_at");
        updatedAt = rs.getTimestamp("updated_at");
        score = rs.getInt("score");
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM add_review('%s', %d, %d, %d);",
                Message, score, playfieldID, userID);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);
            return true;
        }
        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM update_review(%d, '%s', %d);",
                id, Message, score);

        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next()){
            getValues(rs);
            return true;
        }
        return false;
    }
}
