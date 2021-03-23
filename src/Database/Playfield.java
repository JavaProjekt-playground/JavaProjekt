package Database;

import org.jetbrains.annotations.NotNull;
import sun.security.krb5.internal.crypto.Des;

import java.sql.*;

public class Playfield implements IUpdatableTable, IInsertableTable{
    // ID
    private int ID;
    public int getID(){return ID;}
    // Info
    public String Address;
    public String Title;
    public String Description;
    public String Email;
    public String Phone;
    public String Website;
    // Stats
    private double avgScore;
    public double getAvgScore() {return avgScore;}
    public double PricePerHour;
    private int pendingReservations;
    public int getPendingReservations(){return pendingReservations;}
    // Foreign keys
    public int RegionID;
    public int UserID;
    public int TypeID;
    public int ThumbnailID;
    // Dates
    private Timestamp createdAt;
    public Timestamp getCreatedAt() {return createdAt;}
    private Timestamp updatedAt;
    public Timestamp getUpdatedAt() {return updatedAt;}

    public Playfield(
            String title,
            String description,
            String address,
            String email,
            String phone,
            String website,
            double pricePerHour,
            int userID,
            int regionID,
            int typeID
    ){
        Title = title;
        Description = description;
        Address = address;
        Email = email;
        Phone = phone;
        Website = website;
        PricePerHour = pricePerHour;
        UserID = userID;
        RegionID = regionID;
        TypeID = typeID;
    }

    public Playfield(@NotNull ResultSet rs) throws SQLException {
        getValues(rs);
    }

    private void getValues(ResultSet rs) throws SQLException {
        ID = rs.getInt("id");
        Title = rs.getString("title");
        Description = rs.getString("description");
        Address = rs.getString("address");
        Email = rs.getString("email");
        Phone = rs.getString("phone");
        Website = rs.getString("website");
        PricePerHour = rs.getDouble("price_per_hour");
        UserID = rs.getInt("user_id");
        TypeID = rs.getInt("type_id");
        ThumbnailID = rs.getInt("title_image_id");
        RegionID = rs.getInt("region_id");
        avgScore = rs.getDouble("avg_score");
        pendingReservations = rs.getInt("pending_reservations");
        createdAt = rs.getTimestamp("created_at");
        updatedAt = rs.getTimestamp("updated_at");
    }

    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {

        String sql = String.format("SELECT * FROM add_playfields('%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d, %d);",
            Title,
            Description,
            Phone,
            Email,
            Website,
            Address,
            RegionID,
            UserID,
            TypeID,
            PricePerHour
        );

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            getValues(rs);
            return true;
        }

        return false;
    }

    @Override
    public boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        String sql = String.format("SELECT * FROM update_playfields('%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d, %d, %d);",
                Title,
                Description,
                Phone,
                Email,
                Website,
                Address,
                RegionID,
                UserID,
                ThumbnailID,
                TypeID,
                PricePerHour
        );

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            getValues(rs);
            return true;
        }

        return false;
    }
}
