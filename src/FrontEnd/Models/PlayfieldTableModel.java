package FrontEnd.Models;

import Database.Picture;
import Database.Playfield;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class PlayfieldTableModel extends DefaultTableModel {
    private String[] columnNames = new String[]{
            "Thumbnail", "Description", "Address"
    };
    private Object[][] data = {
            {new Object(), new Object(), new Object()}
    };
    private Playfield[] playfields;

    public PlayfieldTableModel(Vector<Playfield> pf){
        super();

        playfields = new Playfield[pf.size()];
        playfields = pf.toArray(playfields);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return playfields != null ? playfields.length : 0;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch(column){
            case 0:
                return playfields[row].Thumbnail;
            case 1:
                return playfields[row].Description;
            case 2:
                return playfields[row].Address;
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return Picture.class;
            case 1:
            case 2:
                return String.class;
            default:
                return null;
        }
    }

    public Playfield getPlayfield(int rowIndex){
        return playfields[rowIndex];
    }

}
