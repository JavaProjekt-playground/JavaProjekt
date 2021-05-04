package FrontEnd;

import Database.DatabaseManager;
import Database.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginForm {
    private JLabel PrijavaLabel;
    private JTextField EmailTextBox;
    private JPasswordField GesloPasswordField;
    private JLabel GesloLabel;
    private JButton PrijavaButton;
    private JLabel EmailLabel;
    public JPanel Prijava;
    private JButton Registracija;

    public static DatabaseManager DB;

    public LoginForm() {

        DB = new DatabaseManager();

        // Login button function
        PrijavaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Registration button function
        Registracija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToRegister();
            }
        });

    }

    private void login(){
        User user = null;
        try {
            user = LoginForm.DB.userLogin(EmailTextBox.getText(), String.valueOf(GesloPasswordField.getPassword()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(user == null){
            JOptionPane.showMessageDialog(null,
                    "Prijava ni uspela!\nPreverite prijavne podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Navigator.goTo(new Dashboard(user).main,"Nadzorna plošča");
    }

    private void goToRegister(){
        Navigator.goTo(new Register().panel1, "Registracija");
    }
}


