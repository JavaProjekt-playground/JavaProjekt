package FrontEnd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                 JOptionPane.showMessageDialog(null,"hello");
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
