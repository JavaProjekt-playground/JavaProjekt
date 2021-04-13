package FrontEnd;

import Database.DatabaseManager;
import Database.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register {
    private JButton register;
    public JPanel panel1;
    private JTextField name;
    private JTextField bdate;
    private JPasswordField password1;
    private JPasswordField password2;
    private JTextField phone;
    private JTextField email;
    private JTextField surname;
    private JLabel Registration;
    private JButton Back;

    public Register() {
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager db = new DatabaseManager();

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                // you can change format of date
                Date date = null;
                try {
                    date = formatter.parse(bdate.getText());
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                Timestamp timeStampDate = new Timestamp(date.getTime());
                User user = new User(name.getText(), surname.getText(), email.getText(), phone.getText(), timeStampDate);
                try {
                    db.addUser(user, password1.toString());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("LoginForm");
                frame.setContentPane(new LoginForm().Prijava);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("LoginForm");
                frame.setContentPane(new LoginForm().Prijava);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Register");
        frame.setContentPane(new Register().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
