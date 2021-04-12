import Database.DatabaseManager;
import FrontEnd.AddPlayground;

import javax.swing.*;

public class JavaProject {
    public static DatabaseManager db;

    public static void main(String[] args){

        JFrame frame = new JFrame("LoginForm");
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().Prijava);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
