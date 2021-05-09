package Database;

import java.sql.*;

public class Playfield implements IUpdatableTable, IInsertableTable{
    // ID
    private int ID;
    public int getID(){return ID;}
    public void setID(int value){ID = value;}
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

    public Picture Thumbnail;

    public Playfield(
            String title,
            String description,
            String address,
            String email,
            String phone,
            String website,
            int userID,
            int regionID,
            int typeID,
            double pricePerHour
    ){
        Title = title;
        Description = description;
        Address = address;
        Email = email;
        Phone = phone;
        Website = website;
        UserID = userID;
        RegionID = regionID;
        TypeID = typeID;
        PricePerHour = pricePerHour;
    }

    public Playfield(ResultSet rs) throws SQLException {
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
        UserID = rs.getInt("owner_id");
        TypeID = rs.getInt("type_id");
        ThumbnailID = rs.getInt("title_image_id");
        RegionID = rs.getInt("region_id");
        avgScore = rs.getDouble("avg_score");
        pendingReservations = rs.getInt("pending_reservations");
        createdAt = rs.getTimestamp("created_at");
        updatedAt = rs.getTimestamp("updated_at");
    }

    /**
     *
     * @param conn
     * @param extra
     * @return
     * @throws SQLException
     */
    @Override
    public boolean selfInsert(Connection conn, Object... extra) throws SQLException {

        String sql = String.format("SELECT * FROM add_playfields('%s'::VARCHAR, " +
                        "'%s'::TEXT, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "%d::INT, %d::INT, %d::INT, %s::REAL);",
            Title,
            Description,
            Phone,
            Email,
            Website,
            Address,
            RegionID,
            UserID,
            TypeID,
            Double.valueOf(PricePerHour).toString().replace(',', '.')
        );
System.out.println(sql);
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
        String sql = String.format("SELECT * FROM update_playfields('%s'::VARCHAR, " +
                        "'%s'::TEXT, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "'%s'::VARCHAR, " +
                        "%d::INT, %d::INT, %d::INT, %d::INT, %s::REAL);",
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
            Double.valueOf(PricePerHour).toString().replace(',', '.')
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
