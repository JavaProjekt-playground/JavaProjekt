package FrontEnd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    private JButton register;
    private JPanel panel1;
    private JTextField name;
    private JTextField bdate;
    private JPasswordField password1;
    private JPasswordField password2;
    private JTextField phone;
    private JTextField email;
    private JTextField surname;
    private JLabel Registration;

    public Register() {
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(null,"hello");
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
