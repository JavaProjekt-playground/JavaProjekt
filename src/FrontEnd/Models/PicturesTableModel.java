package FrontEnd.Models;

import Database.Picture;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class PicturesTableModel extends DefaultTableModel {

    private final String[] columnNames = {
            "Slika", "Opis", ""
    };

    private Vector<Picture> pictures;
    private int thumbRow;
    private Picture thumbnail;

    public PicturesTableModel(){this(new Vector<>(), -1);}
    public PicturesTableModel(Vector<Picture> data, int thumbID){

        if(data == null) throw new NullPointerException("data cannot be null");
        pictures = data;
        thumbRow = -1;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getId() == thumbID){
                thumbRow = i;
                break;
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Picture.class : Object.class;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Picture p = pictures.get(row);
        switch(column){
            case 0:
                return p;
            case 1:
                return p.Caption;
            case 2:
                return p == thumbnail;
        }

        throw new IllegalArgumentException("Parameter 'column' cannot exceed 2.");
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 2;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return dataVector.size();
//        return pictures == null ? 0 : pictures.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void switchThumbnail(int newRow){
        thumbRow = newRow;
    }

    public Picture getThumbnail(){return pictures.get(thumbRow);}

    public void addPicture(Picture pic){
        System.out.println(getRowCount());
        Vector<Object> row = new Vector<Object>();
        row.add(pic);
        row.add(pic.Caption);
        row.add(pic == thumbnail);
        addRow(row);
        pictures.add(pic);

    }



    public void removePicture(Picture pic){
        int i = pictures.indexOf(pic);
        System.out.println();
        if(i > -1){
            removeRow(i);
            pictures.removeElementAt(i);
        }
    }

}
