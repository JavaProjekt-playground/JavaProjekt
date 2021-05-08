package FrontEnd;

import Database.Playfield;
import Database.User;
import FrontEnd.TableModels.PlayfieldTableModel;
import FrontEnd.TableModels.UserInformation;
import FrontEnd.Update.UpdatePlayground;

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
    private JButton UpdateButton;

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
        UpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = PlayfieldsTable.getSelectedRow();
                Playfield playfield = (Playfield) PlayfieldsTable.getModel().getValueAt(row, 1);
                System.out.println(playfield.Description);
                Navigator.goTo(new UpdatePlayground(playfield).updatePlayground,
                "updatePlayground");
            }
        });
    }
}
