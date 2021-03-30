package Database;

public class Review implements IUpdatableTable, IInsertableTable {
    private int id;
    public int getID(){return id;}
    private int userID;
    public int getUserID() {return userID;}
}
