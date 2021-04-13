import Database.DatabaseManager;
import FrontEnd.AddPlayground;
import FrontEnd.LoginForm;

import javax.swing.*;

public class JavaProject {

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddPlayground");
        frame.setContentPane(new AddPlayground().addplayground);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
