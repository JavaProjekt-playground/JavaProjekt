package FrontEnd;

import Database.User;
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

    public User User;

    public Dashboard(User user){
        super();

        User = user;

        PlayfieldTableModel model;
        try {
            model = new PlayfieldTableModel(LoginForm.DB.getPlayfields(10));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            model = null;
        }

        PlayfieldsTable.setModel(model);
    }
}
