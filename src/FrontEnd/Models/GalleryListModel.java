package FrontEnd.Models;

import Database.Picture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class GalleryListModel extends DefaultListModel<Picture> {

    private Vector<Vector<Picture>> pictures;

    public GalleryListModel(){
        this(new Vector<>());
    }
    public GalleryListModel(Vector<Picture> pics){
        for(Picture p : pics){
            addElement(p);
        }
    }

}
