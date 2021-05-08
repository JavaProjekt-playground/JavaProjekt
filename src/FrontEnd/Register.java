package FrontEnd;

import Database.User;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Register {
    private JButton registerButton;
    public JPanel panel1;
    private JTextField nameTF;
    private JTextField bdate;
    private JPasswordField password1PF;
    private JPasswordField password2PF;
    private JTextField phoneTF;
    private JTextField emailTF;
    private JTextField surnameTF;
    private JLabel Registration;
    private JButton goBackButton;


    public Register() {
        registerButton.addActionListener(e -> registerUser());
        goBackButton.addActionListener(e -> App.goBack());
    }

    private void registerUser(){
        String pass1 = String.valueOf(password1PF.getPassword());
        String pass2 = String.valueOf(password2PF.getPassword());
        String name = nameTF.getText();
        String email = emailTF.getText();
        String surname = surnameTF.getText();
        String phone = phoneTF.getText();
        Timestamp bDate = new Timestamp(0);

        if(!pass1.equals(pass2)){
            JOptionPane.showMessageDialog(panel1, "Passwords don't match.");
            return;
        }

        if(name.equals("") || surname.equals("")){
            JOptionPane.showMessageDialog(panel1, "Input name and surname.");
            return;
        }

        if(email.equals("")){
            JOptionPane.showMessageDialog(panel1, "Input email");
            return;
        }

        User newUser = new User(
                name, surname, email, phone, bDate
        );

        try {
            App.DB.addUser(newUser, pass1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Service error.");
            return;
        }

        App.login(newUser);
    }

}
