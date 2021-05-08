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

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return "Login";
    }

    public LoginForm() {

        // Login button function
        PrijavaButton.addActionListener(e -> login());

        // Registration button function
        Registracija.addActionListener(e -> goToRegister());
    }

    private void login(){
        User user = null;

        String email = EmailTextBox.getText();
        String pass = String.valueOf(GesloPasswordField.getPassword());
        try {
            user = App.DB.userLogin(email, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(user == null){
            JOptionPane.showMessageDialog(null,
                    "Prijava ni uspela!\nPreverite prijavne podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println(user.Name + "  " + user.Surname);

        App.login(user);
    }

    private void goToRegister(){
        App.goTo(new Register());
    }
}


