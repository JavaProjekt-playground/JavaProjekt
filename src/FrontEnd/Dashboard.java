package FrontEnd;

import Database.Picture;
import Database.Playfield;
import Database.User;
import FrontEnd.TableModels.CellRenderers.PictureCellRenderer;
import FrontEnd.TableModels.PlayfieldTableModel;

import javax.swing.*;
import java.sql.SQLException;

public class Dashboard {
    public JPanel main;
    private JButton OrderButton;
    private JButton SearchButton;
    private JButton SettingsButton;
    private JTable PlayfieldsTable;
    private JTextField SearchTextField;
    private JButton SelectButton;
    private JButton AddButton;
    private JButton UpdateButton;

    public User User;

    public Dashboard(){
        super();

        PlayfieldTableModel model;
        try {
            model = new PlayfieldTableModel(App.DB.getPlayfields(10));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            model = null;
        }

        PlayfieldsTable.setModel(model);
        PlayfieldsTable.setDefaultRenderer(Picture.class, new PictureCellRenderer());
    }

    private void goToPlayFieldEditor(){
        goToPlayfieldEditor(null);
    }

    private void goToPlayfieldEditor(Playfield field){

        String title = field == null ? "New playfield" : "Edit - " + field.Title;

        App.goTo(new PlayfieldEditor(field).addplayground, title);
    }
}