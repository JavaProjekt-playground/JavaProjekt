package FrontEnd;

import Database.DatabaseManager;
import Database.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginForm implements IFormWindow{
    private JLabel PrijavaLabel;
    private JTextField EmailTextBox;
    private JPasswordField GesloPasswordField;
    private JLabel GesloLabel;
    private JButton PrijavaButton;
    private JLabel EmailLabel;
    public JPanel mainPanel;
    private JButton Registracija;

    public String title = "Login";

    //    public static DatabaseManager DB;

    public LoginForm() {

//        DB = new DatabaseManager();

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
            user = App.DB.userLogin(EmailTextBox.getText(), String.valueOf(GesloPasswordField.getPassword()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(user == null){
            JOptionPane.showMessageDialog(null,
                    "Prijava ni uspela!\nPreverite prijavne podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        App.login(user);
    }

    private void goToRegister(){
        App.goTo(new Register());
    }
}


