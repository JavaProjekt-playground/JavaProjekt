package FrontEnd;

import Database.User;
import FrontEnd.TableModels.PlayfieldTableModel;
import FrontEnd.TableModels.UserInformation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public User User;

    public Dashboard(User user){
        super();

        UserInformation userInformation = new UserInformation(user);

        PlayfieldTableModel model;
        try {
            model = new PlayfieldTableModel(LoginForm.DB.getPlayfields(10));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            model = null;
        }

        PlayfieldsTable.setModel(model);

        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.goTo(new AddPlayground().addplayground,"addplayground");
            }
        });
    }
}
