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

    public LoginForm() {
        PrijavaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager db = new DatabaseManager();
                try {
//                    System.out.println(EmailTextBox.getText() + "   " + String.valueOf(GesloPasswordField.getPassword()));
                    User user = db.userLogin(EmailTextBox.getText(), String.valueOf(GesloPasswordField.getPassword()));
//                    System.out.println(user.Name);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        Registracija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Register");
                frame.setContentPane(new Register().panel1);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
    }

    public static void main(String[] args){

    }
}


