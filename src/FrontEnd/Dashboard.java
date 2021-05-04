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

    private static User user;
    public static User getUser(){
        return user;
    }
    public static void setUser(User value){
        user = value;
    }


    public Dashboard(User loggeduser){
        super();

        setUser(loggeduser);

        PlayfieldTableModel model;
        try {
            model = new PlayfieldTableModel(LoginForm.DB.getPlayfields(10));
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

    }
}
