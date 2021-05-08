package FrontEnd;

import Database.Picture;
import Database.Playfield;
import Database.User;
import FrontEnd.TableModels.CellRenderers.PictureCellRenderer;
import FrontEnd.TableModels.PlayfieldTableModel;

import javax.swing.*;
import java.sql.SQLException;

public class Dashboard implements IFormWindow{
    public JPanel mainPanel;
    private JButton OrderButton;
    private JButton SearchButton;
    private JButton SettingsButton;
    private JTable PlayfieldsTable;
    private JTextField SearchTextField;
    private JButton SelectButton;
    private JButton AddButton;
    private JButton UpdateButton;

    public String title;

    public Dashboard() {
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

        title = String.format("Dashboard - %s %s", App.getCurrentUser().Name, App.getCurrentUser().Surname);
    }

    private void goToPlayFieldEditor(){
        goToPlayfieldEditor(null);
    }

    private void goToPlayfieldEditor(Playfield field){

        String title = field == null ? "New playfield" : "Edit - " + field.Title;

        App.goTo(new PlayfieldEditor(field));
    }
}