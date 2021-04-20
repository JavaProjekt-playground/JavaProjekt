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
                User user = null;
                try {
//                    System.out.println(EmailTextBox.getText() + "   " + String.valueOf(GesloPasswordField.getPassword()));
                    user = LoginForm.DB.userLogin(EmailTextBox.getText(), String.valueOf(GesloPasswordField.getPassword()));
                    System.out.println(user.Name);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if(user == null){
                    JOptionPane.showMessageDialog(null,
                            "Prijava ni uspela!\nPreverite prijavne podatke.", "Napaka", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                JFrame frame = new JFrame("Playfields");
                frame.setContentPane(new Dashboard(user).main);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

                Prijava.setVisible(false);
            }
        });

        // Registration button function
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
}


