package FrontEnd;

import Database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SettingsEditor implements IFormWindow {
    private JPanel mainPanel;
    private JTextField nameTF;
    private JButton EditButton;
    private JSpinner bdateSpinner;
    private JTextField phoneTF;
    private JTextField emailTF;
    private JTextField surnameTF;
    private JLabel Registration;
    private JButton goBackButton;
    private JPanel Settings;
    private JPasswordField checkPasswordPF;
    private JPasswordField oldPasswordPF;
    private JPasswordField newPasswordPF;
    private JButton ChangePassword;
    private JLabel newpassword;
    private JLabel checknewpassword;
    private JLabel oldpassword;

    private boolean Mode = false;
    private boolean Pass = false;
    private User user = App.getCurrentUser();

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public SettingsEditor() {
        App.canGoBack();
//        addObjects();
        setPlayfield(Mode);
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Mode == false) {
                    Mode = true;
                    setPlayfield(Mode);
                } else {
//                    Insert();
                    Mode = false;
                    setPlayfield(Mode);
                }
            }
        });
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Mode) {
                    Mode = false;
                    setPlayfield(Mode);
                } else {
                    Mode = true;

                    App.goBack();
                }
            }
        });
        ChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pass = true;
                newPasswordPF.setEnabled(true);
                checkPasswordPF.setEnabled(true);
                oldPasswordPF.setEnabled(true);
            }
        });
    }

//    private void Insert(){
//        User NewUser = new User(
//                nameTF.getText(),
//                surnameTF.getText(),
//                emailTF.getText(),
//                phoneTF.getText(),
//                UtilsH.convertStringToTimestamp(bdateSpinner.getText())
//        );
//        if(Pass){
//            try {
//                if(String.valueOf(newPasswordPF.getPassword()).equals(String.valueOf(checkPasswordPF.getPassword()))){
//                    {App.DB.updateUser1(user.getID(),NewUser, String.valueOf(oldPasswordPF.getPassword()), String.valueOf(newPasswordPF.getPassword()));
//                    App.EditUser(NewUser);}
//                }
//                else {
//                    JOptionPane.showMessageDialog(mainPanel,
//                            "Gesli se ne ujemata!\nPreverite podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
//                }
//            } catch (SQLException throwables) {
//                JOptionPane.showMessageDialog(mainPanel,
//                        "Prijava ni uspela!\nPreverite prijavne podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
//                throwables.printStackTrace();
//            }
//        }
//        else {
//            try {
//                App.DB.updateUser1(user.getID(),NewUser, String.valueOf(oldPasswordPF.getPassword()), "test");
//                App.EditUser(NewUser);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }

//    private void addObjects() {
//
//        nameTF.setText(user.Name);
//        surnameTF.setText(user.Surname);
//        bdateSpinner.setText(user.BDate.toString());
//        emailTF.setText(user.Email);
//        phoneTF.setText(user.Phone);
//    }


    public void setPlayfield(boolean EditMode) {
        if (EditMode) {
            nameTF.setEnabled(true);
            surnameTF.setEnabled(true);
            bdateSpinner.setEnabled(true);
            emailTF.setEnabled(true);
            phoneTF.setEnabled(true);
            ChangePassword.setVisible(true);
            newPasswordPF.setVisible(true);
            checkPasswordPF.setVisible(true);
            oldPasswordPF.setVisible(true);
            newpassword.setVisible(true);
            checknewpassword.setVisible(true);
            oldpassword.setVisible(true);
            oldPasswordPF.setEnabled(true);
            EditButton.setText("Potrdi");
            goBackButton.setText("Prekliči");
        } else {
            nameTF.setEnabled(false);
            surnameTF.setEnabled(false);
            bdateSpinner.setEnabled(false);
            emailTF.setEnabled(false);
            phoneTF.setEnabled(false);
            ChangePassword.setVisible(false);
            newPasswordPF.setVisible(false);
            checkPasswordPF.setVisible(false);
            oldPasswordPF.setVisible(false);
            newpassword.setVisible(false);
            checknewpassword.setVisible(false);
            oldpassword.setVisible(false);
            checkPasswordPF.setEnabled(false);
            oldPasswordPF.setEnabled(false);
            newPasswordPF.setEnabled(false);
        }
        //title = playfield == null ? "Add playfield" : "Edit playfield: " + playfield.Title;
    }

}
